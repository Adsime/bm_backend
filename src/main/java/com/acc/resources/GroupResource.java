package com.acc.resources;

import com.acc.controller.Controller;
import com.acc.models.Group;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by melsom.adrian on 02.02.2017.
 */

@Path("groups")
public class GroupResource {

    @Inject
    public Controller controller;

    @GET
    @Path("ping")
    public String groupPong(@Context HttpHeaders headers) {
        controller.verify(headers.getRequestHeader(HttpHeaders.AUTHORIZATION).get(0));
        return "group pong!";
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    /**
     *
     */
    public Response getGroup(@PathParam("id") int id, @Context HttpHeaders headers) {
        if(!controller.verify(headers.getRequestHeader(HttpHeaders.AUTHORIZATION).get(0))) {
            return Response.status(403).build();
        }
        Group group = controller.findGroup(id);
        if(group != null) {
            return Response.ok(group.toString()).build();
        }
        return Response.status(404).build();
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
