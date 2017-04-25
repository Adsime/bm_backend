package com.acc.resources;

import com.acc.google.FileHandler;
import com.acc.models.Folder;
import com.acc.service.FileService;

import java.io.*;
import javax.inject.Inject;
import javax.json.JsonObject;
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

    @POST
    @Path("/folder")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createFolder(JsonObject o) {
        Folder folder = new Gson().fromJson(o.toString(), Folder.class);
        if(folder.getName() == null || folder.getParent() == null) {
            return Response.status(HttpStatus.BAD_REQUEST_400).entity("Ufullstendig informasjon.\n" +
                    "Format: \n{\n" +
                    " name: <FolderName>,\n" +
                    " parent: <Parent ID>\n" +
                    "}").build();
        }
        return service.createFolder(folder);
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

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteItem(@PathParam("id") String id,
                               @DefaultValue("false") @QueryParam("forced") boolean forced) {
        Response response = service.deleteItem(id, forced);
        return response;
    }

    @POST
    @Path("/test/{name}")
    @Consumes(MediaType.TEXT_HTML)
    public Response upload(String html, @PathParam("name") String name) {
        try {
            try {
                WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
                MainDocumentPart mdp = wordMLPackage.getMainDocumentPart();

                mdp.addAltChunk(AltChunkType.Xhtml, html.getBytes());
                mdp.addParagraphOfText("Hello world");

                String filename = System.getProperty("user.dir") + "/" + name + ".docx";
                File file = new File(filename);

                WordprocessingMLPackage fin = mdp.convertAltChunks();

                // Saving the file locally, then uploads to the server.
                Docx4J.save(fin, file, Docx4J.FLAG_SAVE_ZIP_FILE);
                service.saveFile(file, name, findType(file.getName(), true), findType(file.getName(), false));

                // Ensures the file is deleted from the server storage after it's been uploaded.
                file.delete();
            } catch (Docx4JException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {

        }
        return Response.ok().build();
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail) {
        String fileLocation = fileDetail.getFileName();
        String[] split = fileLocation.split("\\.");
        String extension = "." + split[split.length -1];
        String type = findType(fileLocation, true);
        String originalType = findType(fileLocation, false);

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
