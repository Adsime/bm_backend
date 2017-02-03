package com.acc.resources;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.json.JsonObject;
import javax.validation.OverridesAttribute;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by melsom.adrian on 03.02.2017.
 */

@Path("problems")
public class ProblemController {

    @GET
    @Path("ping")
    public String problemPong() {
        return "problem pong!";
    }

    @GET
    @Path("{id}")
    /**
     *
     */
    public Response getProblem(@PathParam("id") int id) {
        throw new NotImplementedException();
    }

    @GET
    /**
     *
     */
    public Response getAllProblems() {
        throw new NotImplementedException();
    }

    @POST
    /**
     *
     */
    public Response newProblem(JsonObject o) {
        throw new NotImplementedException();
    }

    @DELETE
    @Path("{id}")
    /**
     *
     */
    public Response deleteProblem(@PathParam("id") int id) {
        throw new NotImplementedException();
    }

    @PUT
    /**
     *
     */
    public Response updateProblem() {
        throw new NotImplementedException();
    }
}
