package com.acc.resources;

import com.acc.models.User;
import com.acc.service.UserService;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Before;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by melsom.adrian on 03.02.2017.
 */

@Path("users")
public class UserResource {

    private static final Logger LOGGER = Logger.getLogger("application");

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
        LOGGER.info("ACTION: GET - user | ID = " + id);
        try {
            return service.getUser(id);
        } catch (Exception e) {
            LOGGER.error("Unexpected exception!", e);
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
        LOGGER.info("ACTION: GET - user | QUERY:\n" + tags);
        try {
            if(tags.isEmpty()){
              return service.getAllUsers();
            }
            return service.getUserByTags(tags, hasAll);
        } catch (Exception e) {
            LOGGER.error("Unexpected exception!", e);
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
        LOGGER.info("ACTION: POST - user | ITEM =\n" + o.toString());
        try {
            User user = new Gson().fromJson(o.toString(), User.class);
            return service.newUser(user);
        } catch (Exception e) {
            LOGGER.error("Unexpected exception!", e);
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
        LOGGER.info("ACTION: DELETE - user | ID = " + id);
        try {
            return service.deleteUser(id, forced);
        } catch (Exception e) {
            LOGGER.error("Unexpected exception!", e);
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
        LOGGER.info("ACTION: PUT - user | ITEM = \n" + o.toString());
        try {
            User user = new Gson().fromJson(o.toString(), User.class);
            return service.updateUser(user);
        } catch (Exception e) {
            LOGGER.error("Unexpected exception!", e);
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }
}
