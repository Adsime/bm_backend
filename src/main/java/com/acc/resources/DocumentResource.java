package com.acc.resources;

import com.acc.models.Document;
import com.acc.service.DocumentService;
import com.google.gson.Gson;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Before;

import javax.inject.Inject;
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

@Path("documents")
public class DocumentResource {

    @Inject
    public DocumentService service;

    @Before
    public void setup() {}

    @GET
    @Path("ping")
    public String documentPong() {
        return "document pong!";
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDocument(@PathParam("id") int id, @Context HttpHeaders headers) {
        System.out.println("ACTION: GET - document | id = " + id);
        try {
            Document document = service.getDocument(id);
            if(document == null) {
                return Response.status(HttpStatus.BAD_REQUEST_400).build();
            }
            return Response.ok(document.toString()).build();
        } catch (InternalServerErrorException isee) {
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDocuments(@Context HttpHeaders headers) {
        System.out.println("ACTION: GET - document | ALL");
        try {
            List<Document> documents = service.getAllDocuments();
            if(documents == null || documents.isEmpty()) {
                return Response.status(HttpStatus.BAD_REQUEST_400).build();
            }
            return Response.ok(new Gson().toJson(documents)).build();
        } catch (InternalServerErrorException isee) {
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newDocument(@Context HttpHeaders headers, JsonObject o) {
        System.out.println("ACTION: POST - document | document:\n" + o);
        try {
            Document document = new Gson().fromJson(o.toString(), Document.class);
            document = service.newDocument(document);
            if(document != null) {
                return Response.status(HttpStatus.CREATED_201).build();
            }
            return Response.status(HttpStatus.NOT_ACCEPTABLE_406).build();

        } catch (InternalServerErrorException isee) {
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDocument(@Context HttpHeaders headers, JsonObject o) {
        System.out.println("ACTION: UPDATE - document | document:\n" + o);
        try {
            Document document = new Gson().fromJson(o.toString(), Document.class);
            if(!service.updateDocument(document)) {
                return Response.status(HttpStatus.BAD_REQUEST_400).build();
            }
            return Response.ok().build();
        } catch (InternalServerErrorException isee) {
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteDocument(@PathParam("id") int id, @Context HttpHeaders headers) {
        System.out.println("ACTION: DELETE - document | id = " + id);
        try {
            if(!service.deleteDocument(id)) {
                return Response.status(HttpStatus.BAD_REQUEST_400).build();
            }
            return Response.status(HttpStatus.NO_CONTENT_204).build();
        } catch (InternalServerErrorException isee) {
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }
}
