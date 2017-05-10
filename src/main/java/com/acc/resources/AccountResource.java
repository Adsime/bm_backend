package com.acc.resources;

import com.acc.service.AccountService;

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

    @Context
    private ContainerRequestContext context;

    @Inject
    private AccountService service;

    @GET
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login() {
        return service.verifyUser(context.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("password")
    public Response updatePassword(JsonObject o) {
        String password = o.getString("password");
        Response response = service.setNewPassword(password);
        return response;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("newUser/{id}")
    public Response createLogin(@PathParam("id") long id) {
        service.resetPassword(id);
        return Response.ok("Mail sendt!").build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("noUse")
    public Response temp(JsonObject o) {
        long id = (long)o.getInt("id");
        String password = o.getString("password");
        service.temp(id, password);
        return Response.ok().build();
    }

}
