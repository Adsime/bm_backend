package com.acc.resources;

import com.acc.google.FileHandler;
import com.acc.service.FileService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.apache.http.HttpResponse;
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
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFolderContent(@PathParam("id") String id, @Context HttpHeaders headers) {
        if(service.verify(headers.getRequestHeader(HttpHeaders.AUTHORIZATION).get(0))) {

        }
        return Response.status(HttpStatus.UNAUTHORIZED_401).build();
    }

    @GET
    @Path("/docs")
    @Produces(MediaType.TEXT_HTML)
    public String get() {
        try {
            URI target = new URI();
            FileHandler fileHandler = new FileHandler();
            String token = fileHandler.authorize().getAccessToken();
            org.apache.http.client.HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet("https://docs.google.com/spreadsheets/d/17_tGxvUKmWK_VTgXPQg-QDEa918GTj2Rje-bDV1SX7o/edit?usp=drivesdk");
            get.setHeader("Authorization", "Bearer ya29.GlwYBBiYMg-2_uqtrgS7_U2ironLwK-4JGzs_QMR32MVz-Y5phPBWPYfl5R0jVUXhgRzGvtIsNXGpq6AXVERA_GNm6M6E0W56tdFkKG6vhzEpGBlImPeWzI3bf-Nyw");
            HttpResponse response = client.execute(get);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*@POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postFile(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail) {
        try {
            String ddoooood = "duy";
            /*byte[] bytes = new byte[1024];
            int read = 0;
            OutputStream os = new FileOutputStream(File.createTempFile("temp", "asd"));
            while((read = inputStream.read(bytes)) != -1) {
                os.write(bytes, 0, read);
            }
            System.out.println("Hei");
            return Response.status(HttpStatus.OK_200).build();
        } catch (Exception e) {

        }
        System.out.println("Hei");
        return Response.status(HttpStatus.BAD_REQUEST_400).build();
    }*/

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail) {
        String fileLocation = fileDetail.getFileName();
        String[] split = fileLocation.split("\\.");
        String extension = "." + split[split.length -1];
        String type = findType(fileLocation, false);
        String originalType = findType(fileLocation, true);

        if(type == null || originalType == null) {
            return Response.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE_415)
                    .entity(extension + " is currently not a supported file format. Please contact an admin for support.").build();
        }
        //saving file
        try {
            File file = File.createTempFile(fileLocation, fileLocation);
            int read = 0;
            byte[] bytes = new byte[1024];
            FileOutputStream out = new FileOutputStream(file);
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            service.saveFile(file, fileLocation, type, originalType);
            out.flush();
            out.close();
            } catch (IOException e) {e.printStackTrace();}
        String output = "File successfully uploaded to : " + fileLocation;
        return Response.status(200).entity(output).build();
    }

    public String findType(String fileName, boolean googleType) {
        if(fileName.endsWith(".docx")) {
            return (googleType) ? "application/vnd.google-apps.document"
                : "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        } else if(fileName.endsWith(".pptx")) {
            return (googleType) ? "application/vnd.google-apps.presentation"
                : "application/vnd.openxmlformats-officedocument.presentationml.presentation";
        } else if(fileName.endsWith(".xlsx")) {
            return (googleType) ? "application/x-vnd.oasis.opendocument.spreadsheet"
            : "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        }
        return null;
    }

}
