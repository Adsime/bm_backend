package com.acc.resources;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by melsom.adrian on 02.02.2017.
 */

@Path("groups")
public class GroupResource {

    @GET
    @Path("ping")
    public String groupPong() {
        return "group pong!";
    }

    @GET
    @Path("{id}")
    /**
     *
     */
    public Response getGroup() {
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
