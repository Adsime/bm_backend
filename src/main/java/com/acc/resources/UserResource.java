package com.acc.resources;

import com.acc.database.repository.Repository;
import com.acc.models.Tag;
import com.acc.models.User;
import com.acc.service.UserService;
import com.google.gson.Gson;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Before;
import sun.security.util.Resources_it;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

/**
 * Created by melsom.adrian on 03.02.2017.
 */

@Path("users")
public class UserResource {

    @Inject
    public UserService service;

    @Before
    public void setup() {
    }

    @GET
    @Path("ping")
    public String userPong() {
        return "User pong!";
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    /**
     *
     */
    public Response getUser(@PathParam("id") int id, @Context HttpHeaders headers) {
        System.out.println("ACTION: GET - user | ID = " + id);
        try {
            User user = service.getUser(id);
            if(user == null) {
                return Response.status(HttpStatus.BAD_REQUEST_400).build();
            }
            return Response.ok(user.toString()).build();
        } catch(InternalServerErrorException isee) {
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    /**
     *
     */
    public Response queryUsers(@Context HttpHeaders headers,
                               @QueryParam("tags") List<String> tags,
                               @DefaultValue("true") @QueryParam("hasAll") boolean hasAll) {
        System.out.println("ACTION: GET - user | QUERY:\n" + tags);
        try {
            List<User> users = tags.isEmpty() ? service.getAllUsers() : service.getUserByTags(tags, hasAll);
            if(users == null || users.isEmpty()) {
                return Response.status(HttpStatus.BAD_REQUEST_400).build();
            }
            return Response.ok(new Gson().toJson(users)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    /**
     *
     */
    public Response newUser(JsonObject o, @Context HttpHeaders headers) {
        System.out.println("ACTION: POST - user | ITEM =\n" + o.toString());
        try {
            User user = new Gson().fromJson(o.toString(), User.class);
            if((user = service.newUser(user)) != null) {
                return Response.status(HttpStatus.CREATED_201).entity(user.toString()).build();
            }
            return Response.status(HttpStatus.NOT_ACCEPTABLE_406).build();
        } catch(Exception e) {
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @DELETE
    @Path("{id}")
    /**
     *
     */
    public Response deleteUser(@PathParam("id") int id, @Context HttpHeaders headers) {
        System.out.println("ACTION: DELETE - user | ID = " + id);
        try {
            if(service.deleteUser(id)) {
                return Response.status(HttpStatus.NO_CONTENT_204).build();
            }
            return Response.status(HttpStatus.BAD_REQUEST_400).build();
        } catch(InternalServerErrorException isee) {
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @PUT
    /**
     *
     */
    public Response updateUser(JsonObject o, @Context HttpHeaders headers) {
        System.out.println("ACTION: PUT - user | ITEM = \n" + o.toString());
        try {
            if(service.updateUser(new Gson().fromJson(o.toString(), User.class))) {
                return Response.ok().build();
            }
            return Response.status(HttpStatus.BAD_REQUEST_400).build();
        } catch(InternalServerErrorException isee) {
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }
}
