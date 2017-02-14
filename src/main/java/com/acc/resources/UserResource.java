package com.acc.resources;

import com.acc.controller.ProblemService;
import com.acc.controller.UserService;
import com.google.gson.Gson;
import org.junit.Before;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by melsom.adrian on 03.02.2017.
 */

@Path("users")
public class UserResource {

    @Inject
    public UserService service;

    public Gson gson;

    @Before
    public void setup() {
        gson = new Gson();
    }

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
