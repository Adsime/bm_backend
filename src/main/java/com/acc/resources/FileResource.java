package com.acc.resources;

import com.acc.google.FileHandler;
import com.acc.models.Folder;
import com.acc.models.Tag;
import com.acc.service.FileService;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.persistence.PostRemove;
import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.http.HttpResponse;
import org.docx4j.Docx4J;
import org.docx4j.Docx4jProperties;
import org.docx4j.XmlUtils;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.convert.out.HTMLSettings;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.AltChunkType;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.sun.org.apache.xml.internal.utils.URI;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.eclipse.jetty.http.HttpStatus;
import org.hibernate.annotations.UpdateTimestamp;
import org.junit.Before;


/**
 * Created by melsom.adrian on 23.03.2017.
 */

@Path("/files")
public class FileResource {
    @Inject
    private FileService service;

    @Before
    public void setup() {
    }

    @GET
    @Path("/folder/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFolderContent(@PathParam("id") String id, @Context HttpHeaders headers) {
        return service.getFolderContent(id);
    }

    @GET
    @Path("/folder")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFolderContent(@Context HttpHeaders headers) {
        return service.getFolderContent(null);
    }

    @GET
    @Path("/open/{id}")
    @Produces(MediaType.TEXT_HTML)
    public Response getFileAsHtml(@PathParam("id") String id) {
        return Response.ok(service.getFileAsHtml(id)).build();
    }

    @GET
    @Path("/download/{id}")
    @Produces(MediaType.MULTIPART_FORM_DATA)
    public Response download(@PathParam("id") String id) {
        return service.download(id);
    }

    @GET
    @Path("/structure")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStructure() {
        return service.getFolderStructure();
    }

    @GET
    @Path("/assignments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response queryFiles(){
        System.out.println("ACTION: GET - assignemtns");
        try {
            return service.queryAssigntments();
        } catch (InternalServerErrorException isee) {
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @POST
    @Path("/folder")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createFolder(JsonObject o) {
        Folder folder = new Gson().fromJson(o.toString(), Folder.class);
        if (folder.getName() == null || folder.getParent() == null) {
            return Response.status(HttpStatus.BAD_REQUEST_400).entity("Ufullstendig informasjon.\n" +
                    "Format: \n{\n" +
                    " name: <FolderName>,\n" +
                    " parent: <Parent ID>\n" +
                    "}").build();
        }
        return service.createFolder(folder);
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteItem(@PathParam("id") String id,
                               @DefaultValue("false") @QueryParam("forced") boolean forced) {
        System.out.println("\n__________________________________\n performing a delete :\n\n ");
        return service.deleteItem(id, forced);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response updateFile(@FormDataParam("file") InputStream uploadedInputStream,
                               @FormDataParam("file") FormDataContentDisposition fileDetail,
                               @DefaultValue("null") @PathParam("id") String id,
                               @QueryParam("tags") List<Integer> tagIdList) {

        return service.updateFile(uploadedInputStream, fileDetail, id, tagIdList);
    }

    @POST
    @Path("/upload/{id}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadFile(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail,
            @DefaultValue("null") @PathParam("id") String id,
            @DefaultValue("false") @QueryParam("forced") boolean forced,
            @QueryParam("tags") List<Integer> tagIdList) {

        return service.upLoadAnyFile(uploadedInputStream, fileDetail, id, forced, tagIdList);
    }
}
