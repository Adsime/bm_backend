package com.acc.resources;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.json.JsonObject;
import javax.validation.OverridesAttribute;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by melsom.adrian on 03.02.2017.
 */

@Path("problems")
public class ProblemResource {

    @GET
    @Path("ping")
    public String problemPong() {
        return "problem pong!";
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProblem(@PathParam("id") int id, @Context HttpHeaders headers) {
        throw new NotImplementedException();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProblems(@Context HttpHeaders headers) {
        throw new NotImplementedException();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newProblem(@Context HttpHeaders headers) {
        throw new NotImplementedException();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProblem(@Context HttpHeaders headers) {
        throw new NotImplementedException();
    }

    @DELETE
    @Path("{id}")
    public Response deleteProblem(@Context HttpHeaders headers) {
        throw new NotImplementedException();
    }
}
