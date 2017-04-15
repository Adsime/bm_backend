package com.acc.resources;

import com.acc.models.Group;
import com.acc.service.GeneralService;
import com.acc.service.GroupService;
import com.google.gson.Gson;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Before;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.print.attribute.standard.Media;
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


    @Before
    public void setup() {}

    @GET
    @Path("ping")
    public String groupPong(@Context HttpHeaders headers) {
        return "group pong!";
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    /**
     *
     */
    public Response getGroup(@PathParam("id") int id, @Context HttpHeaders headers) {
        System.out.println("ACTION: GET - group | ID = " + id);
        try {
            return service.getGroup(id);
        }catch(InternalServerErrorException isee) {
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    /**
     *
     */
    public Response queryGroups(@Context HttpHeaders headers,
                                @QueryParam("tags") List<String> tags,
                                @DefaultValue("true") @QueryParam("hasAll") boolean hasAll) {
        System.out.println("ACTION: GET - group | QUERY. tags = " + tags + " hasAll = " + hasAll);
        try {
            if(tags.isEmpty()) {
                return service.getAllGroups();
            }
            return service.getGroupByTags(tags, hasAll);
        } catch (InternalServerErrorException isee) {
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    /**
     *
     */
    public Response newGroup(JsonObject o, @Context HttpHeaders headers) {
        System.out.println("ACTION: POST - GROUP | item = \n" + o.toString());
        try {
            Group group = new Gson().fromJson(o.toString(), Group.class);
            return service.newGroup(group);
        } catch (InternalServerErrorException isee) {
            System.out.println(isee.toString());
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @DELETE
    @Path("{id}")
    /**
     *
     */
    public Response deleteGroup(@PathParam("id") int id, @Context HttpHeaders headers) {
        System.out.println("ACTION: DELETE - user | ID = " + id);
        try {
            return service.deleteGroup(id);
        } catch (InternalServerErrorException isee) {
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    /**
     *
     */
    public Response updateGroup(@Context HttpHeaders headers, JsonObject o) {
        System.out.println("ACTION: GET - user | item = \n" + o.toString());
        try {
            Group group = new Gson().fromJson(o.toString(), Group.class);
            return service.updateGroup(group);
        }catch (InternalServerErrorException isee) {
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }
}
