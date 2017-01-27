package com.acc.controller;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by melsom.adrian on 26.01.2017.
 */

@Path("user")
public class UserController {

    @POST
    @Path("Register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerUser() {
        throw new NotImplementedException();
    }

}
