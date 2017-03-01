package com.acc.resources;

import com.acc.models.Tag;
import com.acc.models.User;
import com.acc.service.UserService;
import com.google.gson.Gson;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Before;

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
        if(service.verify(headers.getRequestHeader(HttpHeaders.AUTHORIZATION).get(0))) {
            try {
                User user = service.getUser(id);
                if(user == null) {
                    return Response.status(HttpStatus.BAD_REQUEST_400).build();
                }
                return Response.ok(user.toJson()).build();
            } catch(InternalServerErrorException isee) {
                return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
            }
        } return Response.status(HttpStatus.UNAUTHORIZED_401).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    /**
     *
     */
    public Response getAllUsers(@Context HttpHeaders headers) {
        System.out.println("ACTION: GET - user | ALL");
        if(service.verify(headers.getRequestHeader(HttpHeaders.AUTHORIZATION).get(0))) {
            try {
                List<User> users = service.getAllUsers();
                if(users == null || users.isEmpty()) {
                    return Response.status(HttpStatus.BAD_REQUEST_400).build();
                }
                return Response.ok(users.toString()).build();
            } catch(Exception e) {
                return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
            }
        } return Response.status(HttpStatus.UNAUTHORIZED_401).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    /**
     *
     */
    public Response newUser(JsonObject o, @Context HttpHeaders headers) {
        System.out.println("ACTION: POST - user | ITEM =\n" + o.toString());
        if(service.verify(headers.getRequestHeader(HttpHeaders.AUTHORIZATION).get(0))) {
            try {
                User user = new Gson().fromJson(o.toString(), User.class);
                System.out.println(user.toJson());
                if(service.newUser(user)) {
                    return Response.ok().build();
                }
            } catch(Exception e) {
                return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
            }
        } return Response.status(HttpStatus.UNAUTHORIZED_401).build();
    }

    @DELETE
    @Path("{id}")
    /**
     *
     */
    public Response deleteUser(@PathParam("id") int id, @Context HttpHeaders headers) {
        System.out.println("ACTION: DELETE - user | ID = " + id);
        if(service.verify(headers.getRequestHeader(HttpHeaders.AUTHORIZATION).get(0))) {
            try {
                if(service.deleteUser(id)) {
                    return Response.ok().build();
                }
                return Response.status(HttpStatus.BAD_REQUEST_400).build();
            } catch(InternalServerErrorException isee) {
                return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
            }
        } return Response.status(HttpStatus.UNAUTHORIZED_401).build();
    }

    @PUT
    /**
     *
     */
    public Response updateUser(JsonObject o, @Context HttpHeaders headers) {
        System.out.println("ACTION: PUT - user | ITEM = \n" + o.toString());
        if(service.verify(headers.getRequestHeader(HttpHeaders.AUTHORIZATION).get(0))) {
            try {
                if(service.updateUser(new Gson().fromJson(o.toString(), User.class))) {
                    return Response.ok().build();
                }
                return Response.status(HttpStatus.BAD_REQUEST_400).build();
            } catch(InternalServerErrorException isee) {
                return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
            }
        } return Response.status(HttpStatus.UNAUTHORIZED_401).build();
    }
}
