package com.acc.resources;

import com.acc.controller.GroupService;
import com.acc.models.Group;
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
 * Created by melsom.adrian on 02.02.2017.
 */

@Path("groups")
public class GroupResource {

    @Inject
    public GroupService service;

    public Gson gson;

    @Before
    public void setup() {
        gson = new Gson();
    }

    @GET
    @Path("ping")
    public String groupPong(@Context HttpHeaders headers) {
        service.verify(headers.getRequestHeader(HttpHeaders.AUTHORIZATION).get(0));
        return "group pong!";
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    /**
     *
     */
    public Response getGroup(@PathParam("id") int id, @Context HttpHeaders headers) {
        if(!service.verify(headers.getRequestHeader(HttpHeaders.AUTHORIZATION).get(0))) {
            return Response.status(HttpStatus.UNAUTHORIZED_401).build();
        }
        Group group = service.getGroup(id);
        if(group != null) {
            return Response.ok(group.toString()).build();
        }
        return Response.status(HttpStatus.BAD_REQUEST_400).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    /**
     *
     */
    public Response getAllGroups(@Context HttpHeaders headers) {
        if(!service.verify(headers.getRequestHeader(HttpHeaders.AUTHORIZATION).get(0))) {
            return Response.status(HttpStatus.UNAUTHORIZED_401).build();
        }
        try {
            List<Group> groups = service.getAllGroups();
            if(groups.size() < 1) {
                return Response.status(HttpStatus.BAD_REQUEST_400).build();
            }
            return Response.ok(groups).build();
        } catch (InternalServerErrorException isee) {
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @POST
    /**
     *
     */
    public Response newGroup(JsonObject o, @Context HttpHeaders headers) {
        if(!service.verify(headers.getRequestHeader(HttpHeaders.AUTHORIZATION).get(0))) {
            return Response.status(HttpStatus.UNAUTHORIZED_401).build();
        }
        try {
            Group group = new Gson().fromJson(o.toString(), Group.class);
            service.newGroup(group);
            return Response.status(HttpStatus.CREATED_201).build();
        } catch (InternalServerErrorException isee) {
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @DELETE
    @Path("{id}")
    /**
     *
     */
    public Response deleteGroup(@PathParam("id") int id, @Context HttpHeaders headers) {
        if(!service.verify(headers.getRequestHeader(HttpHeaders.AUTHORIZATION).get(0))) {
            return Response.status(HttpStatus.UNAUTHORIZED_401).build();
        }
        try {
            if(service.deleteGroup(id)) {
                return Response.status(HttpStatus.NO_CONTENT_204).build();
            }
            return Response.status(HttpStatus.BAD_REQUEST_400).build();
        } catch (InternalServerErrorException isee) {
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    /**
     *
     */
    public Response updateGroup(@PathParam("id") int id, @Context HttpHeaders headers, JsonObject body) {
        if(!service.verify(headers.getRequestHeader(HttpHeaders.AUTHORIZATION).get(0))) {
            return Response.status(HttpStatus.UNAUTHORIZED_401).build();
        }
        try {
            Group group = new Gson().fromJson(body.toString(), Group.class);
            if(service.updateGroup(group)) {
                return Response.ok().build();
            }
            return Response.status(HttpStatus.BAD_REQUEST_400).build();
        }catch (InternalServerErrorException isee) {
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }
}
