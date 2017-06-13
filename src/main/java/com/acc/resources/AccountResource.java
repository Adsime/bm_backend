package com.acc.resources;

import com.acc.service.AccountService;
import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by melsom.adrian on 28.03.2017.
 */
@Path("accounts")
public class  AccountResource {

    private static Logger LOGGER = Logger.getLogger("application");

    @Context
    private ContainerRequestContext context;

    @Inject
    private AccountService service;

    @GET
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login() {
        try {
            return service.verifyUser(context.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0));
        } catch (Exception e) {
            LOGGER.error("Unexpected exception!", e);
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("password")
    public Response updatePassword(JsonObject o) {
        try {
            String password = o.getString("password");
            Response response = service.setNewPassword(password);
            return response;
        } catch (Exception e) {
            LOGGER.error("Unexpected exception!", e);
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("newUser/{id}")
    public Response createLogin(@PathParam("id") long id) {
        try {
            service.resetPassword(null, id);
            return Response.ok("Mail sendt!").build();
        } catch (Exception e) {
            LOGGER.error("Unexpected exception!", e);
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response forgotPassword(JsonObject o) {
        String email;
        try {
            email = o.getString("email");
        } catch (Exception e) {
            return Response.status(HttpStatus.BAD_REQUEST_400).entity("Feil format på forespørselen").build();
        }
        return service.resetPassword(email);
    }
}
