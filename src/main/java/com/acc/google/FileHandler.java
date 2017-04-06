package com.acc.google;

import com.acc.models.Problem;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by melsom.adrian on 09.03.2017.
 */
public class FileHandler {

    /** App name */
    private static final String APPLICATION_NAME = "Bachelor Manager";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/bachelor-manager");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    private static final List<String> SCOPES =
            Arrays.asList(DriveScopes.DRIVE);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    public Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
                DriveApi.class.getResourceAsStream("/client_secret.json"); //API key
                //DriveApi.class.getResourceAsStream("/local_key.json"); //Local key
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(DATA_STORE_FACTORY)
                        .setAccessType("offline")
                        .build();
        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");
        return credential;
    }

    /**
     * Build and return an authorized Drive client service.
     * @return an authorized Drive client service
     * @throws IOException
     */
    public Drive getDriveService() throws IOException {
        Credential credential = authorize();
        return new Drive.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /** End google required methods
        Start of custom methods for API calls to google drive API */

    /**
     * Grabs all folders from the application Google Drive. It then creates a tree structure
     * by comparing the parents of the items in the list.
     * @return List of Folders
     */
    public List<Folder> getTreeStructure() {
        try {
            Drive service = getDriveService();
            FileList result = service.files().list()
                    .setQ("trashed = false and mimeType = 'application/vnd.google-apps.folder'")
                    .setFields("nextPageToken, files(id, name, parents, mimeType)")
                    .execute();
            List<File> files = result.getFiles();
            Collections.reverse(files);
            return build(files, files.get(0).getParents().get(0));
        } catch (IOException ioe) {
            return Arrays.asList();
        }
    }

    public String uploadAnyFile(java.io.File file, String name, String type, String originalType) {
        try {
            Drive service = getDriveService();
            File googleFile = new File();
            googleFile.setName(name);
            googleFile.setMimeType(type);
            FileContent fileContent = new FileContent(originalType, file);
            File retFile = service.files().create(googleFile, fileContent)
                    .setFields("id")
                    .execute();
            return retFile.getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Finds the child items of a folder based on ID
     * @param id
     * @return List of google files, which includes folders
     */
    public List<File> getFolder(String id) {
        try {
            Drive service = getDriveService();
            FileList res = service.files().list()
                    .setQ("'" + id + "'" + " in parents" + " and trashed = false")
                    .setFields("nextPageToken, files(id, name, mimeType, iconLink, webViewLink)")
                    .execute();
            return res.getFiles();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return Arrays.asList();
        }
    }

    /**
     * Will update an existing file on Google Drive based on content and name.
     * @param id
     * @param newName
     * @param newContent
     * @return boolean indicating if the action was successfull.
     */
    public boolean updateFile(String id, String newName, String newContent) {
        Path path = null;
        try {
            Drive service = getDriveService();

            /** Downloading a specific file from Google Drive */
            OutputStream outputStream = getFileContent(id, service);
            File file = service
                    .files()
                    .get(id)
                    .execute();

            /** Creating a file locally to update the file stored on Google Drive */
            File updatedFile = new File()
                    .setName(newName == null ? file.getName() : newName);
            FileContent fileContent = createFileContent(file.getName(), outputStream.toString(), newName, newContent, "text/plain");
            path = fileContent.getFile().toPath();

            /** Uploading the new file to replace the content of the current file */
            service.files().update(id, updatedFile, fileContent).setUseContentAsIndexableText(true).execute();

            /** Cleaning up file from cache */
            Files.delete(path);
            return true;
        } catch (IOException ioe) {
            //TODO write to log file instead
            ioe.printStackTrace();
            if(path != null) {
                try {
                    Files.delete(path);
                } catch (IOException ioEx) {
                    //TODO write to log file
                    ioEx.printStackTrace();
                }
            }
            return false;
        }
    }

    public OutputStream asdasd(String id) {
        try {
            Drive service = getDriveService();
            return getFileContent(id, service);
        }catch (IOException ioe) {
            return null;
        }
    }

    private OutputStream getFileContent(String id, Drive service) throws IOException {
        OutputStream outputStream = new ByteArrayOutputStream();
        service.files().export(id, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet").executeMediaAndDownloadTo(outputStream);
        return outputStream;
    }

    /**
     * Creates a new file which is pushed on to Google Drive.
     * @param name
     * @param content
     * @param parents
     * @return boolean indicating if the action was successful.
     */
    public String createFile(String name, String content, List<String> parents) {
        Path path = null;
        String id = null;
        try {
            Drive service = getDriveService();

            /** Creates a file and sets appropriate values */
            FileContent fileContent = createFileContent(name, content, null, null, "text/plain");
            path = fileContent.getFile().toPath();
            File file = new File();
            file.setName(name);
            file.setParents(parents);
            file.setMimeType("application/vnd.google-apps.document");

            /** Tried to upload the file to Google drive */
            File temp = service.files().create(file, fileContent).execute();

            /** Deletes the file from local cache */
            Files.delete(path);
            id = temp.getId();
            return id;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            try {
                Files.delete(path);
                if(id != null) {
                    deleteFile(id);
                }
            } catch (IOException ioEx) {

            }
            return null;
        }
    }

    public boolean deleteFile(String id) {
        try {
            Drive service = getDriveService();
            service.files().delete(id).execute();
            return true;
        } catch (IOException ioe) {
            return false;
        }
    }

    private FileContent createFileContent(String oldName, String oldContent, String newName, String newContent, String type) throws IOException {
        String name = (newName == null || newName.isEmpty() ? oldName : newName);
        List<String> content = new ArrayList<>();
        content.add(newContent == null ? oldContent : newContent);
        java.io.File file = java.io.File.createTempFile(name, null); // new java.io.File(Config.getCacheDirectory(), name);
        Files.write(Paths.get(file.getPath()), content, Charset.forName("UTF-8"));
        return new FileContent(type, file);
    }

    public Problem insertFileContent(Problem problem) {
        try {
            Drive service = getDriveService();
            problem.setContent(getFileContent(problem.getPath(), service).toString());
            return problem;
        } catch (IOException ioe) {
            return null;
        }
     }

    private List<Folder> build(List<File> list, String root) {
        ArrayList<Folder> folders = new ArrayList<>();
        if(list.isEmpty()) return folders;
        File file;
        Iterator<File> it = list.iterator();
        while(it.hasNext() && (file = it.next()) != null) {
            if(file.getParents().get(0).equals(root)) {
                folders.add(new Folder(file));
                list.remove(file);
                it = list.iterator();
            }

        }
        for(Folder f : folders) {
            f.setChildren(build(list, f.getFolder().getId()));
        }
        return folders;
    }
}
