package com.acc.resources;

import com.acc.models.User;
import com.acc.service.UserService;
import com.google.gson.Gson;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Before;

import javax.inject.Inject;
import javax.json.JsonObject;
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
    public void setup() {}

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
            return service.getUser(id);
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
            if(tags.isEmpty()){
              return service.getAllUsers();
            }
            return service.getUserByTags(tags, hasAll);
        } catch (InternalServerErrorException e) {
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
            return service.newUser(user);
        } catch(InternalServerErrorException isee) {
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
            return service.deleteUser(id);
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
            User user = new Gson().fromJson(o.toString(), User.class);
            return service.updateUser(user);
        } catch(InternalServerErrorException isee) {
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }
}
