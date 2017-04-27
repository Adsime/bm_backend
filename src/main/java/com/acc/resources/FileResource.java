package com.acc.resources;

import com.acc.google.FileHandler;
import com.acc.models.Folder;
import com.acc.service.FileService;

import java.io.*;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.persistence.PostRemove;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.google.gson.Gson;
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
        Response response = service.deleteItem(id, forced);
        return response;
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail) {
        return service.upLoadAnyFile(uploadedInputStream, fileDetail);
    }

}
