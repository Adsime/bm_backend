package com.acc.resources;

import com.acc.database.repository.ProblemRepository;
import com.acc.database.repository.TagRepository;
import com.acc.database.repository.UserRepository;
import com.acc.models.Problem;
import com.acc.service.ProblemService;
import com.google.gson.Gson;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Before;
import org.omg.CORBA.RepositoryIdHelper;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by melsom.adrian on 03.02.2017.
 */

@Path("problems")
public class ProblemResource {

    @Inject
    public ProblemService service;

    @Before
    public void setup() {

    }

    @GET
    @Path("ping")
    public String problemPong() {
        return "problem pong!";
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProblem(@PathParam("id") int id, @Context HttpHeaders headers) {
        System.out.println("ACTION: GET - problem | id = " + id);
        if(service.verify(headers.getRequestHeader(HttpHeaders.AUTHORIZATION).get(0))) {
            try {
                Problem problem = service.getProblem(id);
                if(problem == null) {
                    return Response.status(HttpStatus.BAD_REQUEST_400).build();
                }
                return Response.ok(problem.toString()).build();
            } catch (InternalServerErrorException isee) {
                return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
            }
        } return Response.status(HttpStatus.UNAUTHORIZED_401).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProblems(@Context HttpHeaders headers) {
        System.out.println("ACTION: GET - problem | ALL");
        if(service.verify(headers.getRequestHeader(HttpHeaders.AUTHORIZATION).get(0))) {
            try {
                List<Problem> problems = service.getAllProblems();
                if(problems == null || problems.isEmpty()) {
                    return Response.status(HttpStatus.BAD_REQUEST_400).build();
                }
                return Response.ok(new Gson().toJson(problems)).build();
            } catch (InternalServerErrorException isee) {
                return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
            }
        } return Response.status(HttpStatus.UNAUTHORIZED_401).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newProblem(@Context HttpHeaders headers, JsonObject o) {
        System.out.println("ACTION: POST - problem | problem:\n" + o);
        if(service.verify(headers.getRequestHeader(HttpHeaders.AUTHORIZATION).get(0))) {
            try {
                Problem problem = new Gson().fromJson(o.toString(), Problem.class);
                problem = service.newProblem(problem);
                if(problem != null) {
                    return Response.status(HttpStatus.CREATED_201).build();
                }
                return Response.status(HttpStatus.BAD_REQUEST_400).build();

            } catch (InternalServerErrorException isee) {
                return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
            }
        } return Response.status(HttpStatus.UNAUTHORIZED_401).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProblem(@Context HttpHeaders headers, JsonObject o) {
        System.out.println("ACTION: UPDATE - problem | problem:\n" + o);
        if(service.verify(headers.getRequestHeader(HttpHeaders.AUTHORIZATION).get(0))) {
            try {
                Problem problem = new Gson().fromJson(o.toString(), Problem.class);
                if(!service.updateProblem(problem)) {
                    return Response.status(HttpStatus.BAD_REQUEST_400).build();
                }
                return Response.ok().build();
            } catch (InternalServerErrorException isee) {
                return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
            }
        } return Response.status(HttpStatus.UNAUTHORIZED_401).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteProblem(@PathParam("id") int id, @Context HttpHeaders headers) {
        System.out.println("ACTION: DELETE - problem | id = " + id);
        if(service.verify(headers.getRequestHeader(HttpHeaders.AUTHORIZATION).get(0))) {
            try {
                if(!service.deleteProblem(id)) {
                    return Response.status(HttpStatus.BAD_REQUEST_400).build();
                }
                return Response.status(HttpStatus.NO_CONTENT_204).build();
            } catch (InternalServerErrorException isee) {
                return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
            }
        } return Response.status(HttpStatus.UNAUTHORIZED_401).build();
    }
}
