package com.acc.resources;

import com.acc.models.Tag;
import com.acc.service.TagService;
import com.google.gson.Gson;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Before;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
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
    /**
     *
     */
    public Response getTag(@PathParam("id") int id, @Context HttpHeaders headers) {
        if(service.verify(headers.getRequestHeader(HttpHeaders.AUTHORIZATION).get(0))) {
           try {
               Tag tag = service.getTag(id);
               if(tag == null) {
                   return Response.status(HttpStatus.BAD_REQUEST_400).build();
               }
               return Response.ok(tag.toJson()).build();
           } catch (InternalServerErrorException isee) {
               return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
           }
        } return Response.status(HttpStatus.UNAUTHORIZED_401).build();
    }

    @GET
    /**
     *
     */
    public Response getAllTags(@Context HttpHeaders headers) {
        if(service.verify(headers.getRequestHeader(HttpHeaders.AUTHORIZATION).get(0))) {
            try {
                List<Tag> tags = service.getAllTags();
                if(tags == null || tags.isEmpty()) {
                    return Response.status(HttpStatus.BAD_REQUEST_400).build();
                }
                JsonArrayBuilder jab = Json.createArrayBuilder();
                for(Tag tag : tags) {
                    jab.add(tag.toJson());
                }
                return Response.ok(jab.build()).build();
            } catch (InternalServerErrorException isee) {
                return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
            }
        } return Response.status(HttpStatus.UNAUTHORIZED_401).build();
    }

    @POST
    /**
     *
     */
    public Response newTag(JsonObject o, @Context HttpHeaders headers) {
        if(service.verify(headers.getRequestHeader(HttpHeaders.AUTHORIZATION).get(0))) {
            try {
                if(service.newTag(new Gson().fromJson(o.toString(), Tag.class))) {
                    return Response.ok().build();
                }
            } catch (InternalServerErrorException isee) {
                return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
            }
        } return Response.status(HttpStatus.UNAUTHORIZED_401).build();
    }

    @DELETE
    @Path("{id}")
    /**
     *
     */
    public Response deleteTag(@PathParam("id") int id, @Context HttpHeaders headers) {
        if(service.verify(headers.getRequestHeader(HttpHeaders.AUTHORIZATION).get(0))) {
            try {
                if(!service.deleteTag(id)) {
                    return Response.status(HttpStatus.BAD_REQUEST_400).build();
                }
                return Response.ok().build();
            } catch (InternalServerErrorException isee) {
                return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
            }
        } return Response.status(HttpStatus.UNAUTHORIZED_401).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    /**
     *
     */
    public Response updateTag(JsonObject o, @Context HttpHeaders headers) {
        if(service.verify(headers.getRequestHeader(HttpHeaders.AUTHORIZATION).get(0))) {
            try {
                if(!service.updateTag(new Gson().fromJson(o.toString(), Tag.class))) {
                    return Response.status(HttpStatus.BAD_REQUEST_400).build();
                }
                return Response.ok(o).build();
            } catch (InternalServerErrorException isee) {
                return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
            }
        } return Response.status(HttpStatus.UNAUTHORIZED_401).build();
    }
}
