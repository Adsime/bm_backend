package com.acc.resources;

import com.acc.models.User;
import com.acc.service.UserService;
import com.google.gson.Gson;
import org.eclipse.jetty.http.HttpStatus;
import org.glassfish.jersey.logging.LoggingFeature;
import org.junit.Before;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by melsom.adrian on 03.02.2017.
 */

@Path("users")
public class UserResource {

    private static final Logger LOGGER = Logger.getLogger("user");

    static {
        LOGGER.setLevel(Level.INFO);
    }

    @Inject
    public UserService service;

    @Before
    public void setup() {}

    @GET
    @Path("ping")
    public String userPong() {
        return "User pong!";
    }

    /**
     * Endpoint for getting a specific user.
     * @param id int
     * @return Response
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") int id) {
        LOGGER.log(Level.FINEST, "ACTION: GET - user | ID = " + id);
        try {
            return service.getUser(id);
        } catch(InternalServerErrorException e) {
            LOGGER.log(Level.SEVERE, "Unexpected exception caught in method getUser", e);
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    /**
     * Endpoint for getting all users, or get ones with specific tags.
     * @param tags List<String>
     * @param hasAll boolean
     * @return Response
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response queryUsers(@QueryParam("tags") List<String> tags,
                               @DefaultValue("true") @QueryParam("hasAll") boolean hasAll) {
        LOGGER.log(Level.FINEST,"ACTION: GET - user | QUERY:\n" + tags);
        try {
            if(tags.isEmpty()){
              return service.getAllUsers();
            }
            return service.getUserByTags(tags, hasAll);
        } catch (InternalServerErrorException e) {
            LOGGER.log(Level.SEVERE, "Unexpected exception caught in method queryUsers", e);
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    /**
     * Endpoint for creating a new user.
     * @param o JsonObject
     * @return Response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response newUser(JsonObject o) {
        LOGGER.log(Level.FINEST,"ACTION: POST - user | ITEM =\n" + o.toString());
        try {
            User user = new Gson().fromJson(o.toString(), User.class);
            return service.newUser(user);
        } catch(InternalServerErrorException e) {
            LOGGER.log(Level.SEVERE, "Unexpected exception caught in method newUser", e);
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    /**
     * Endpoint for deleting a user.
     * @param id int
     * @param forced boolean
     * @return Response
     */
    @DELETE
    @Path("{id}")
    public Response deleteUser(@PathParam("id") int id,
                               @DefaultValue("false") @QueryParam("forced") boolean forced) {
        LOGGER.log(Level.FINEST,"ACTION: DELETE - user | ID = " + id);
        try {
            return service.deleteUser(id, forced);
        } catch(InternalServerErrorException e) {
            LOGGER.log(Level.SEVERE, "Unexpected exception caught in method deleteUser", e);
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    /**
     * Endpoint for updating a user.
     * @param o JsonObject
     * @return Response
     */
    @PUT
    public Response updateUser(JsonObject o) {
        LOGGER.log(Level.FINEST,"ACTION: PUT - user | ITEM = \n" + o.toString());
        try {
            User user = new Gson().fromJson(o.toString(), User.class);
            return service.updateUser(user);
        } catch(InternalServerErrorException e) {
            LOGGER.log(Level.SEVERE, "Unexpected exception caught in method updateUser", e);
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }
}
