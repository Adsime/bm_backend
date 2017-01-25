package com.acc.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by melsom.adrian on 25.01.2017.
 */

@Path("res")
public class Controller {

    @Path("ping")
    @GET
    public String ping() {
        return "Pong!";
    }

}
