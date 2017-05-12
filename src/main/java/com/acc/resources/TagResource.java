package com.acc.resources;

import com.acc.models.Tag;
import com.acc.service.TagService;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by melsom.adrian on 03.02.2017.
 */

@Path("tags")
public class TagResource {

    private static Logger LOGGER = Logger.getLogger("application");

    @Inject
    public TagService service;

    @GET
    @Path("ping")
    public String tagPong() {
        return "tag pong!";
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTag(@PathParam("id") int id) {
        LOGGER.info("ACTION: GET - tag | id = " + id);
        try {
            return service.getTag(id);
       } catch (InternalServerErrorException isee) {
           return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
       }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response queryTags(@QueryParam("name") List<String> names) {
        LOGGER.info("ACTION: GET - tag | query = " + names);
        try {
            return service.queryTags(names);
        } catch (InternalServerErrorException isee) {
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response newTag(JsonObject o) {
        LOGGER.info("ACTION: POST - tag | tag = " + o.toString());
        try {
            return service.newTag(new Gson().fromJson(o.toString(), Tag.class));
        } catch (InternalServerErrorException isee) {
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteTag(@PathParam("id") int id) {
        LOGGER.info("ACTION: DELETE - tag | id = " + id);
        try {
            return service.deleteTag(id);
        } catch (InternalServerErrorException isee) {
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTag(JsonObject o) {
        LOGGER.info("ACTION: UPDATE - tag | tag = " + o.toString());
        try {
            return service.updateTag(new Gson().fromJson(o.toString(), Tag.class));
        } catch (InternalServerErrorException isee) {
            LOGGER.error("Unexpected error in TagResource.updateTag");
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }
}
