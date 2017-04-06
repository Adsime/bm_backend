package com.acc.resources;

import com.acc.models.Problem;
import com.acc.service.ProblemService;
import com.google.gson.Gson;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Before;

import javax.inject.Inject;
import javax.json.JsonObject;
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
    public void setup() {}

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
        try {
            Problem problem = service.getProblem(id);
            if(problem == null) {
                return Response.status(HttpStatus.BAD_REQUEST_400).build();
            }
            return Response.ok(problem.toString()).build();
        } catch (InternalServerErrorException isee) {
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProblems(@Context HttpHeaders headers) {
        System.out.println("ACTION: GET - problem | ALL");
        try {
            List<Problem> problems = service.getAllProblems();
            if(problems == null || problems.isEmpty()) {
                return Response.status(HttpStatus.BAD_REQUEST_400).build();
            }
            return Response.ok(new Gson().toJson(problems)).build();
        } catch (InternalServerErrorException isee) {
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newProblem(@Context HttpHeaders headers, JsonObject o) {
        System.out.println("ACTION: POST - problem | problem:\n" + o);
        try {
            Problem problem = new Gson().fromJson(o.toString(), Problem.class);
            problem = service.newProblem(problem);
            if(problem != null) {
                return Response.status(HttpStatus.CREATED_201).build();
            }
            return Response.status(HttpStatus.NOT_ACCEPTABLE_406).build();

        } catch (InternalServerErrorException isee) {
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProblem(@Context HttpHeaders headers, JsonObject o) {
        System.out.println("ACTION: UPDATE - problem | problem:\n" + o);
        try {
            Problem problem = new Gson().fromJson(o.toString(), Problem.class);
            if(!service.updateProblem(problem)) {
                return Response.status(HttpStatus.BAD_REQUEST_400).build();
            }
            return Response.ok().build();
        } catch (InternalServerErrorException isee) {
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteProblem(@PathParam("id") int id, @Context HttpHeaders headers) {
        System.out.println("ACTION: DELETE - problem | id = " + id);
        try {
            if(!service.deleteProblem(id)) {
                return Response.status(HttpStatus.BAD_REQUEST_400).build();
            }
            return Response.status(HttpStatus.NO_CONTENT_204).build();
        } catch (InternalServerErrorException isee) {
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }
}
