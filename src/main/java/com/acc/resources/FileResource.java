package com.acc.resources;

import com.acc.google.FileHandler;
import com.acc.service.FileService;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.sun.org.apache.xerces.internal.util.URI;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Before;
import sun.net.www.http.HttpClient;

import javax.inject.Inject;
import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

/**
 * Created by melsom.adrian on 23.03.2017.
 */

@Path("files")
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

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postFile(@FormDataParam("file") InputStream fileStream) {
        System.out.println(fileStream);
        return Response.status(HttpStatus.OK_200).build();
    }
}
