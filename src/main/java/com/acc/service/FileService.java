package com.acc.service;

import com.acc.google.FileHandler;
import com.acc.models.Folder;
import com.google.api.services.drive.model.File;
import com.google.gson.Gson;
import org.eclipse.jetty.http.HttpStatus;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by melsom.adrian on 23.03.2017.
 */
public class FileService extends GeneralService {

    @Inject
    private FileHandler fileHandler;

    public boolean saveFile(java.io.File file, String name, String type, String originalType) {
        String res = fileHandler.uploadAnyFile(file, name, type, originalType);
        return res != null;
    }

    public Response getFolderContent(String id) {
        try {
            List<File> files = fileHandler.getFolder(id);
            return Response.status(HttpStatus.OK_200).entity(new Gson().toJson(files)).build();
        } catch (Exception e) {
            return Response.status(HttpStatus.BAD_REQUEST_400).entity("Invalid folder ID provided.").build();
        }
    }

    public Response createFolder(Folder folder) {
        int created = fileHandler.createFolder(folder);
        int status = (created == FileHandler.CREATED_201) ? HttpStatus.OK_200
                : (created == FileHandler.EXISTS_400) ? HttpStatus.MULTIPLE_CHOICES_300
                : HttpStatus.BAD_REQUEST_400;
        String entity = (created == FileHandler.CREATED_201) ? folder.getName() + " er opprettet!"
                : (created == FileHandler.EXISTS_400) ? folder.getName() + " eksisterer allerede. \nØnkser du å opprette en mappe med samme navn?"
                : "Var ikke i stand til å opprette " + folder.getName();
        Response response = Response
                                .status(status)
                                .entity(entity)
                                .build();
        return response;
    }

    public Response deleteItem(String id, boolean forced) {
        int status = fileHandler.deleteItem(id, forced);
        return Response.status(status).build();
}
}
