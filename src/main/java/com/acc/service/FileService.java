package com.acc.service;

import com.acc.google.FileHandler;
import com.google.api.services.drive.model.File;
import com.google.gson.Gson;
import org.apache.http.protocol.HTTP;
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
}
