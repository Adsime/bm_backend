package com.acc.service;

import com.acc.database.repository.DocumentRepository;
import com.acc.google.FileHandler;
import com.acc.google.GoogleFolder;
import com.acc.models.Document;
import com.acc.models.Error;
import com.acc.models.Folder;
import com.acc.models.Tag;
import com.acc.requestContext.BMSecurityContext;
import com.google.api.services.drive.model.File;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
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
import javax.persistence.EntityNotFoundException;

import javax.ws.rs.core.Response;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by melsom.adrian on 23.03.2017.
 */
public class FileService extends GeneralService {

    @Inject
    private FileHandler fileHandler;

    @Inject
    private DocumentRepository documentRepository;

    /*@Context
    private BMSecurityContext securityContext;
    */

    public void setFileHandler(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public String saveFile(java.io.File file, String name, String type, String originalType, String parent) {
        String res = fileHandler.uploadAnyFile(file, name, type, originalType, parent);
        return res;
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
        // TODO: 01.05.2017  documentRepository.remove()
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

    private String findType(String fileName, boolean googleType) {
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

    public Response updateFile(InputStream content, FormDataContentDisposition fileDetail, String id, JsonArray o) {
        String fileName = fileDetail.getFileName();
        String[] split = fileName.split("\\.");
        String extension = "." + split[split.length - 1];
        java.io.File file;

        /*String authorEId = securityContext.getUser().getName();
        int authorId;
        try {
            authorId = documentRepository.findAuthorId(authorEId);
        }catch (EntityNotFoundException enfe){
            Error error = new Error(enfe.getMessage());
            return Response.status(HttpStatus.UNAUTHORIZED_401).entity(error.toJson()).build();
        }*/

        if(extension.equals(".html")) {
            file = createFileFromHtml(content, fileDetail.getName(), extension);
        } else {
            file = createTempFile(fileDetail.getName(), content);
        }

        // TODO: 01.05.2017  Document document = new Document(,authorId,fileName,"content", id, toTagList(o));

        /*try{
            documentRepository.update(document);
        }catch (EntityNotFoundException enfe) {
            Error error = new Error(enfe.getMessage());
            return Response.status(HttpStatus.BAD_REQUEST_400).entity(error.toJson()).build();
        }*/

        fileHandler.updateAnyFile(file, id, findType(extension, false));
        file.delete();

        return null;
    }

    private java.io.File createFileFromHtml(InputStream content, String name, String extension) {
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

    public Response upLoadAnyFile(InputStream uploadedInputStream,
                                  FormDataContentDisposition fileDetail,
                                  String parent, boolean forced, List<Integer> tagIdList) {
        String fileName = fileDetail.getFileName();
        String[] split = fileName.split("\\.");
        String extension = "." + split[split.length - 1];
        String type = findType(fileName, true);
        String originalType = findType(fileName, false);

        //String authorEId = securityContext.getUser().getName();
        String authorEId = "silva.david";
        int authorId;
        try {
            authorId = documentRepository.findAuthorId(authorEId);
        }catch (EntityNotFoundException enfe){
            Error error = new Error(enfe.getMessage());
            return Response.status(HttpStatus.UNAUTHORIZED_401).entity(error.toJson()).build();
        }

        if ((type == null || originalType == null) && !extension.equals(".html")) {
            return Response.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE_415)
                    .entity(extension + " is currently not a supported file format. Please contact an admin for support.").build();
        }

        String apiId = fileHandler.exists(fileName, parent, false);
        if(apiId != null && !forced) {
            Response response = Response.status(HttpStatus.MULTIPLE_CHOICES_300).entity("{" +
                    "\"message\": \"Fil med samme navn eksisterer allerede i denne mappen!\"," +
                    "\"id\": \"" + apiId + "\"}").build();
            return response;
        }

        if(extension.toLowerCase().equals(".html")) {
            Response response = uploadAsHtml(uploadedInputStream, fileName.replace(".html", ""), parent, authorId, tagIdList);
            return response;
        }

        //saving file
        java.io.File file = createTempFile(fileName, uploadedInputStream);
        saveFile(file, fileName, type, originalType, parent);
        file.delete();
        String output = "File successfully uploaded to : " + fileName;

        //saving to database
        Document document = new Document(0,authorId,fileName,"content", apiId, toTagList(tagIdList));
        try{
            document = documentRepository.add(document);
        }catch (IllegalArgumentException iae) {
            Error error = new Error(iae.getMessage());
            return Response.status(HttpStatus.NOT_ACCEPTABLE_406).entity(error.toJson()).build();
        }catch (EntityNotFoundException enfe) {
            Error error = new Error(enfe.getMessage());
            return Response.status(HttpStatus.BAD_REQUEST_400).entity(error.toJson()).build();
        }

        return Response.status(HttpStatus.CREATED_201).entity(document.toJson()).build();
    }

    public Response uploadAsHtml(InputStream content, String name, String parent, int authorId, List<Integer> tagIdList) {

        java.io.File file = createFileFromHtml(content, name, ".docx");

        if(file != null) {
            String path = saveFile(file, name, findType(file.getName(), true), findType(file.getName(), false), parent);
            Document document = new Document(0, authorId, name,"content", path, toTagList(tagIdList));
            try{
                documentRepository.add(document);
            }catch (IllegalArgumentException iae) {
                Error error = new Error(iae.getMessage());
                return Response.status(HttpStatus.NOT_ACCEPTABLE_406).entity(error.toJson()).build();
            }catch (EntityNotFoundException enfe) {
                Error error = new Error(enfe.getMessage());
                return Response.status(HttpStatus.BAD_REQUEST_400).entity(error.toJson()).build();
            }

            file.delete();
            return Response.status(HttpStatus.CREATED_201).entity(document.toJson()).build();
        }
        return Response.status(HttpStatus.BAD_REQUEST_400).entity("Could not upload " + name).build();
    }

    public Response getFolderStructure() {
        List<GoogleFolder> folders = fileHandler.getTreeStructure();
        return Response.ok(new Gson().toJson(folders)).build();
    }

    private List<Tag> toTagList(List<Integer> idList){
        List<Tag> tagList = new ArrayList<>();
        idList.forEach(id->tagList.add(new Tag(id,"","","")));
        System.out.println(Arrays.toString(tagList.toArray()));
        return tagList;
    }
}
