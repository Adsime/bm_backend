package com.acc.service;

import com.acc.google.FileHandler;
import com.acc.models.Folder;
import com.google.api.services.drive.model.File;
import com.google.gson.Gson;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.docx4j.Docx4J;
import org.docx4j.Docx4jProperties;
import org.docx4j.convert.out.HTMLSettings;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.AltChunkType;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.eclipse.jetty.http.HttpStatus;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.List;

/**
 * Created by melsom.adrian on 23.03.2017.
 */
public class FileService extends GeneralService {

    @Inject
    private FileHandler fileHandler;

    public void setFileHandler(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public boolean saveFile(java.io.File file, String name, String type, String originalType, String parent) {
        String res = fileHandler.uploadAnyFile(file, name, type, originalType, parent);
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

    public Response download(String id) {
        OutputStream os = fileHandler.downloadAnyFile(id);
        MimeBodyPart osPart = new MimeBodyPart();
        try {
            osPart.setContent(os, "multipart/form-data");
        } catch (MessagingException me) {

        }
        Response response = Response.status(os == null ? HttpStatus.BAD_REQUEST_400 : HttpStatus.OK_200)
                .entity(os == null ? "Var ikke i stand til å hente filen" : osPart).build();

        return response;
    }

    public String getFileAsHtml(String id) {
        try {
            Logger.getRootLogger().setLevel(Level.ERROR);
            WordprocessingMLPackage wordprocessingMLPackage = WordprocessingMLPackage.createPackage();
            java.io.File file = java.io.File.createTempFile("asdasd", ".docx");
            Docx4J.save(wordprocessingMLPackage, file, Docx4J.FLAG_SAVE_ZIP_FILE);
            fileHandler.createFile(id, file);

            WordprocessingMLPackage out = Docx4J.load(file);
            HTMLSettings htmlSettings = Docx4J.createHTMLSettings();

            String inputfilepath = file.getPath();

            htmlSettings.setImageDirPath(inputfilepath + "_files");
            htmlSettings.setImageTargetUri(inputfilepath.substring(inputfilepath.lastIndexOf("/")+1)
                    + "_files");
            htmlSettings.setWmlPackage(out);

            OutputStream os = new ByteArrayOutputStream();

            Docx4jProperties.setProperty("docx4j.Convert.Out.HTML.OutputMethodXML", true);
            Docx4J.toHTML(htmlSettings, os, Docx4J.FLAG_EXPORT_PREFER_XSL);
            file.delete();
            return os.toString();
        } catch (IOException ioe) {

        } catch (Docx4JException de) {

        }
        return null;
    }

    public OutputStream getOutputStreamFromFile(String id, String type) {
        return fileHandler.getFileContent(id, type);
    }

    public String findType(String fileName, boolean googleType) {
        if (fileName.endsWith(".docx")) {
            return (googleType) ? "application/vnd.google-apps.document"
                    : "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        } else if (fileName.endsWith(".pptx")) {
            return (googleType) ? "application/vnd.google-apps.presentation"
                    : "application/vnd.openxmlformats-officedocument.presentationml.presentation";
        } else if (fileName.endsWith(".xlsx")) {
            return (googleType) ? "application/x-vnd.oasis.opendocument.spreadsheet"
                    : "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        }
        return null;
    }

    public Response updateFile(InputStream content, FormDataContentDisposition fileDetail, String id) {
        String fileLocation = fileDetail.getFileName();
        String[] split = fileLocation.split("\\.");
        String extension = "." + split[split.length - 1];
        java.io.File file;
        if(extension.equals(".html")) {
            file = createFileFromHtml(content, fileDetail.getName(), extension);
        } else {
            file = createTempFile(fileDetail.getName(), content);
        }
        fileHandler.updateAnyFile(file, id, findType(extension, false));
        file.delete();

        return null;
    }

    public java.io.File createFileFromHtml(InputStream content, String name, String extension) {
        try {
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
            // Main part of the document. Where the user info is put
            MainDocumentPart mdp = wordMLPackage.getMainDocumentPart();

            // Adds the incoming xhtml to the MainDocumentPart
            mdp.addAltChunk(AltChunkType.Xhtml, content);

            // Creates a new file
            java.io.File file = java.io.File.createTempFile(name, extension);
            /*String filename = System.getProperty("user.dir") + "/" + name + ".docx";
            java.io.File file = new java.io.File(filename);*/

            // Package with the inserted data
            WordprocessingMLPackage fin = mdp.convertAltChunks();
            Docx4J.save(fin, file, Docx4J.FLAG_SAVE_ZIP_FILE);

            return file;
        } catch (Docx4JException de) {
            Logger.getRootLogger().setLevel(Level.INFO);
            de.printStackTrace();
            Logger.getRootLogger().setLevel(Level.ERROR);
        } catch (IOException ioe) {

        }
        return null;
    }

    private java.io.File createTempFile(String name, InputStream inputStream) {
        try {
            java.io.File file = java.io.File.createTempFile(name, name);
            int read = 0;
            byte[] bytes = new byte[1024];
            FileOutputStream out = new FileOutputStream(file);
            while ((read = inputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
            return file;
        } catch (IOException ioe) {

        }
        return null;
    }

    public Response upLoadAnyFile(InputStream uploadedInputStream, FormDataContentDisposition fileDetail, String parent, boolean forced) {
        String fileLocation = fileDetail.getFileName();
        String[] split = fileLocation.split("\\.");
        String extension = "." + split[split.length - 1];
        String type = findType(fileLocation, true);
        String originalType = findType(fileLocation, false);

        if ((type == null || originalType == null) && !extension.equals(".html")) {
            return Response.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE_415)
                    .entity(extension + " is currently not a supported file format. Please contact an admin for support.").build();
        }

        int exists = fileHandler.exists(fileLocation, parent);
        if(exists != 200 && !forced) {
            Response response = Response.status(HttpStatus.MULTIPLE_CHOICES_300).entity("Fil med samme navn eksisterer allerede i denne mappen!").build();
            return response;
        }

        if(extension.toLowerCase().equals(".html")) {
            uploadAsHtml(uploadedInputStream, fileLocation.replace(".html", ""), parent);
            String output = "File successfully uploaded to : " + fileLocation;
            return Response.ok(output).build();
        }

        //saving file
        java.io.File file = createTempFile(fileLocation, uploadedInputStream);
        saveFile(file, fileLocation, type, originalType, parent);
        file.delete();

        String output = "File successfully uploaded to : " + fileLocation;
        return Response.ok(output).build();
    }

    public Response uploadAsHtml(InputStream content, String name, String parent) {

        java.io.File file = createFileFromHtml(content, name, ".docx");
        if(file != null) {
            saveFile(file, name, findType(file.getName(), true), findType(file.getName(), false), parent);

            file.delete();

            return Response.ok(name + " successfully uploaded!").build();
        }
        return Response.status(HttpStatus.BAD_REQUEST_400).entity("Could not upload " + name).build();
    }
}
