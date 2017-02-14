package com.acc.resources;

import com.acc.controller.ProblemService;
import com.acc.controller.TagService;
import com.google.gson.Gson;
import org.junit.Before;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by melsom.adrian on 03.02.2017.
 */

@Path("tags")
public class TagResource {

    @Inject
    public TagService service;

    public Gson gson;

    @Before
    public void setup() {
        gson = new Gson();
    }

    @GET
    @Path("ping")
    public String tagPong() {
        return "tag pong!";
    }

    @GET
    @Path("{id}")
    /**
     *
     */
    public Response getTag(@PathParam("id") int id, @Context HttpHeaders headers) {
        throw new NotImplementedException();
    }

    @GET
    /**
     *
     */
    public Response getAllTags(@Context HttpHeaders headers) {
        throw new NotImplementedException();
    }

    @POST
    /**
     *
     */
    public Response newTag(JsonObject o, @Context HttpHeaders headers) {
        throw new NotImplementedException();
    }

    @DELETE
    @Path("{id}")
    /**
     *
     */
    public Response deleteTag(@PathParam("id") int id, @Context HttpHeaders headers) {
        throw new NotImplementedException();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    /**
     *
     */
    public Response updateTag(JsonObject o, @Context HttpHeaders headers) {
        throw new NotImplementedException();
    }
}
