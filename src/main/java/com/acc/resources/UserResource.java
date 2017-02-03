package com.acc.resources;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by melsom.adrian on 03.02.2017.
 */

@Path("users")
public class UserResource {

    @GET
    @Path("ping")
    public String userPong() {
        return "user pong!";
    }

    @GET
    @Path("{id}")
    /**
     *
     */
    public Response getUser(@PathParam("id") int id) {
        throw new NotImplementedException();
    }

    @GET
    /**
     *
     */
    public Response getAllUsers() {
        throw new NotImplementedException();
    }

    @POST
    /**
     *
     */
    public Response newUser(JsonObject o) {
        throw new NotImplementedException();
    }

    @DELETE
    @Path("{id}")
    /**
     *
     */
    public Response deleteUser(@PathParam("id") int id) {
        throw new NotImplementedException();
    }

    @PUT
    /**
     *
     */
    public Response updateUser() {
        throw new NotImplementedException();
    }
}
