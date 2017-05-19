package com.acc.resources;

import com.acc.models.Folder;
import com.acc.service.FileService;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.junit.Before;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.List;


/**
 * Created by melsom.adrian on 23.03.2017.
 */

@Path("/files")
public class FileResource {

    private static Logger LOGGER = Logger.getLogger("application");

    @Inject
    private FileService service;

    @Before
    public void setup() {
    }

    @GET
    @Path("/folder/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFolderContent(@PathParam("id") String id) {
        try {
            return service.getFolderContent(id);
        } catch (Exception e) {
            LOGGER.error("Unexpected exception!", e);
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @GET
    @Path("/folder")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFolderContent() {
        try {
            return service.getFolderContent(null);
        } catch (Exception e) {
            LOGGER.error("Unexpected exception!", e);
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFileMetadata(@PathParam("id") long id) {
        try {
            return service.getFileMetadata(id);
        } catch (Exception e) {
            LOGGER.error("Unexpected exception!", e);
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @GET
    @Path("/open/{id}")
    @Produces(MediaType.TEXT_HTML)
    public Response getFileAsHtml(@PathParam("id") String id) {
        try {
            return Response.ok(service.getFileAsHtml(id)).build();
        } catch (Exception e) {
            LOGGER.error("Unexpected exception!", e);
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    //WIP
    @GET
    @Path("/download/{id}")
    @Produces(MediaType.MULTIPART_FORM_DATA)
    public Response download(@PathParam("id") String id) {
        try {
            return service.download(id);
        } catch (Exception e) {
            LOGGER.error("Unexpected exception!", e);
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @GET
    @Path("/structure")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStructure() {
        try {
            return service.getFolderStructure();
        } catch (Exception e) {
            LOGGER.error("Unexpected exception!", e);
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @GET
    @Path("root")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRootId() {
        try {
            return service.getRootFolder();
        } catch (Exception e) {
            LOGGER.error("Unexpected exception!", e);
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response queryFiles(@QueryParam("tags") List<String> tags){
        System.out.println("ACTION: GET - assignemtns");
        try {
            if(tags.isEmpty()) {
                return Response.status(HttpStatus.BAD_REQUEST_400).entity("Ingen tags å søke på.").build();
            }
            return service.queryAssigntments(tags);
        } catch (Exception e) {
            LOGGER.error("Unexpected exception!", e);
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @POST
    @Path("/folder")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createFolder(JsonObject o) {
        try {
            Folder folder = new Gson().fromJson(o.toString(), Folder.class);
            if (folder.getName() == null || folder.getParent() == null) {
                return Response.status(HttpStatus.BAD_REQUEST_400).entity("Ufullstendig informasjon.\n" +
                        "Format: \n{\n" +
                        " name: <FolderName>,\n" +
                        " parent: <Parent ID>\n" +
                        "}").build();
            }
            return service.createFolder(folder);
        } catch (Exception e) {
            LOGGER.error("Unexpected exception!", e);
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteItem(@PathParam("id") String id,
                               @DefaultValue("false") @QueryParam("forced") boolean forced) {
        try {
            return service.deleteItem(id, forced);
        } catch (Exception e) {
            LOGGER.error("Unexpected exception!", e);
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response updateFile(@FormDataParam("file") InputStream uploadedInputStream,
                               @FormDataParam("file") FormDataContentDisposition fileDetail,
                               @DefaultValue("null") @PathParam("id") String id,
                               @QueryParam("tags") List<Integer> tagIdList) {
        try {
            return service.updateFile(uploadedInputStream, fileDetail, id, tagIdList);
        } catch (Exception e) {
            LOGGER.error("Unexpected exception!", e);
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @POST
    @Path("/upload/{id}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadFile(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail,
            @DefaultValue("null") @PathParam("id") String parent,
            @DefaultValue("false") @QueryParam("forced") boolean forced,
            @QueryParam("tags") List<Integer> tagIdList) {
        try {
            return service.upLoadAnyFile(uploadedInputStream, fileDetail, parent, forced, tagIdList);
        } catch (Exception e) {
            LOGGER.error("Unexpected exception!", e);
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @GET
    @Path("test")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response test() {
        //File file = service.deleteThis();
        return Response.ok(service.deleteThis()).build();
    }
}
