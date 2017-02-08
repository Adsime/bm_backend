package com.acc.resources;

import com.acc.controller.Controller;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

/**
 * Created by melsom.adrian on 02.02.2017.
 */

@Path("groups")
public class GroupResource {

    private Controller controller;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @GET
    @Path("ping")
    public String groupPong(@Context HttpHeaders headers) {
        Controller c = Controller.getInstance();
        c.verify(headers.getRequestHeader("Authorization").get(0));
        return "group pong!";
    }

    @GET
    @Path("{id}")
    /**
     *
     */
    public Response getGroup(@PathParam("id") int id) {
        throw new NotImplementedException();
    }

    @GET
    /**
     *
     */
    public Response getAllGroups() {
        throw new NotImplementedException();
    }

    @POST
    /**
     *
     */
    public Response newGroup(JsonObject o) {
        throw new NotImplementedException();
    }

    @DELETE
    @Path("{id}")
    /**
     *
     */
    public Response deleteGroup(@PathParam("id") int id) {
        throw new NotImplementedException();
    }

    @PUT
    /**
     *
     */
    public Response updateGroup() {
        throw new NotImplementedException();
    }
}
