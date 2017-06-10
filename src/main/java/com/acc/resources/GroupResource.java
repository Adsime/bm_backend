package com.acc.resources;

import com.acc.models.Group;
import com.acc.service.GroupService;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Before;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
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

    private static Logger LOGGER = Logger.getLogger("application");

    @Inject
    public GroupService service;

    @Context
    private ContainerRequestContext context;

    @Before
    public void setup() {

    }

    @GET
    @Path("ping")
    public String groupPong(@Context HttpHeaders headers) {
        return "group pong!";
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGroup(@PathParam("id") int id) {
        LOGGER.info("ACTION: GET - group | ID = " + id);
        try {
            return service.getGroup(id);
        } catch (Exception e) {
            LOGGER.error("Unexpected exception!", e);
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response queryGroups(@QueryParam("tags") List<String> tags,
                                @DefaultValue("true") @QueryParam("hasAll") boolean hasAll) {
        LOGGER.info("ACTION: GET - group | QUERY. tags = " + tags + " hasAll = " + hasAll);
        try {
            if(tags.isEmpty()) {
                return service.getAllGroups();
            }
            return service.getGroupByTags(tags, hasAll);
        } catch (Exception e) {
            LOGGER.error("Unexpected exception!", e);
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response newGroup(JsonObject o) {
        LOGGER.info("ACTION: POST - GROUP | item = \n" + o.toString());
        try {
            Group group = new Gson().fromJson(o.toString(), Group.class);
            return service.newGroup(group);
        } catch (Exception e) {
            LOGGER.error("Unexpected exception!", e);
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteGroup(@PathParam("id") int id) {
        LOGGER.info("ACTION: DELETE - user | ID = " + id);
        try {
            return service.deleteGroup(id);
        } catch (Exception e) {
            LOGGER.error("Unexpected exception!", e);
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateGroup(JsonObject o) {
        LOGGER.info("ACTION: GET - user | item = \n" + o.toString());
        try {
            Group group = new Gson().fromJson(o.toString(), Group.class);
            return service.updateGroup(group);
        } catch (Exception e) {
            LOGGER.error("Unexpected exception!", e);
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }
}
