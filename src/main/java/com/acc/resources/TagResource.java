package com.acc.resources;

import com.acc.models.Tag;
import com.acc.service.TagService;
import com.google.gson.Gson;
import com.sun.org.apache.regexp.internal.RE;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Before;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by melsom.adrian on 03.02.2017.
 */

@Path("tags")
public class TagResource {

    @Inject
    public TagService service;

    @Before
    public void setup() {

    }

    @GET
    @Path("ping")
    public String tagPong() {
        return "tag pong!";
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    /**
     *
     */
    public Response getTag(@PathParam("id") int id, @Context HttpHeaders headers) {
        System.out.println("ACTION: GET - tag | id = " + id);
        try {
           Tag tag = service.getTag(id);
           if(tag == null) {
               return Response.status(HttpStatus.BAD_REQUEST_400).build();
           }
           return Response.ok(tag.toJson()).build();
       } catch (InternalServerErrorException isee) {
           return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
       }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response queryTags(@Context HttpHeaders headers,
                              @QueryParam("name") List<String> names) {
        System.out.println("ACTION: GET - tah | query = " + names);
        try {
            List<Tag> tags = service.queryTags(names);
            if(tags.isEmpty()) {
                return Response.status(HttpStatus.BAD_REQUEST_400).build();
            }
            return Response.ok(tags.toString()).build();
        } catch (InternalServerErrorException isee) {
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @POST
    /**
     *
     */
    public Response newTag(JsonObject o, @Context HttpHeaders headers) {
        System.out.println("ACTION: POST - tag | tag = " + o.toString());
        try {
            Tag tag = service.newTag(new Gson().fromJson(o.toString(), Tag.class));
            if(tag != null) {
                return Response.status(HttpStatus.CREATED_201).build();
            }
            return Response.status(HttpStatus.NOT_ACCEPTABLE_406).build();
        } catch (InternalServerErrorException isee) {
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @DELETE
    @Path("{id}")
    /**
     *
     */
    public Response deleteTag(@PathParam("id") int id, @Context HttpHeaders headers) {
        System.out.println("ACTION: DELETE - tag | id = " + id);
        try {
            if(!service.deleteTag(id)) {
                return Response.status(HttpStatus.BAD_REQUEST_400).build();
            }
            return Response.status(HttpStatus.NO_CONTENT_204).build();
        } catch (InternalServerErrorException isee) {
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    /**
     *
     */
    public Response updateTag(JsonObject o, @Context HttpHeaders headers) {
        System.out.println("ACTION: UPDATE - tag | tag = " + o.toString());
        try {
            if(!service.updateTag(new Gson().fromJson(o.toString(), Tag.class))) {
                return Response.status(HttpStatus.BAD_REQUEST_400).build();
            }
            return Response.ok(o).build();
        } catch (InternalServerErrorException isee) {
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }
}
