package com.acc.google;

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
import org.glassfish.grizzly.http.server.util.MimeType;

import java.io.*;
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
                DriveApi.class.getResourceAsStream("/client_secret.json");
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
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
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

    public List<File> getFolder(String id) {
        try {
            Drive service = getDriveService();
            FileList res = service.files().list()
                    .setQ("'" + id + "'" + " in parents")
                    .setFields("nextPageToken, files(id, name, mimeType, iconLink, webViewLink)")
                    .execute();
            return res.getFiles();
        } catch (IOException ioe) {
            return Arrays.asList();
        }
    }

    public boolean updateFile(String id, String newName, String newContent) {
        try {
            Drive serive = getDriveService();
            OutputStream outputStream = new ByteArrayOutputStream();
            serive.files().export(id, "text/plain").executeMediaAndDownloadTo(outputStream);
            /*File file = serive
                    .files()
                    .get(id)
                    .execute();
            System.out.println(file.getWebContentLink());
            file.setName(newName == null ? file.getName() : newName)
                    .setDescription(newContent == null ? file.getDescription() : newContent);
            serive.files().update(id, file);*/
            System.out.println(outputStream);
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
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
