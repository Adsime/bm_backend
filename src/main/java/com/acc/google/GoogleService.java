package com.acc.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by melsom.adrian on 29.04.2017.
 */
public class GoogleService {

    public static final String applicationPath = "http://localhost:5555/resetPassord/";

    /** Application name. */
    private static final String APPLICATION_NAME =
            "Bachelor Manager";

    /** Directory to store user credentials for this application. */
    public static final java.io.File DATA_STORE =
            new java.io.File(System.getProperty("user.home"), ".credentials/bm");
            //new java.io.File("./src/main/java/com/acc/google/credentials/mail");

    //public static final java.io.File DATA_STORE_DIR_FILES =
            //new java.io.File(System.getProperty("user.home"), ".credentials/bachelor-manager");
            //new java.io.File("./src/main/java/com/acc/google/credentials/files");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/gmail-java-quickstart
     */
    private static final List<String> SCOPES =
            Arrays.asList(DriveScopes.DRIVE, GmailScopes.MAIL_GOOGLE_COM);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {

        // Load client secrets.
        InputStream in =
                GoogleService.class.getResourceAsStream("/client_secret.json");
                //GoogleService.class.getResourceAsStream("/client_secret.json"); //API key
                //GoogleService.class.getResourceAsStream("/local_key.json"); //Local key
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(DATA_STORE_FACTORY)
                        .setAccessType("offline")
                        .build();

        /*GoogleCredential credential = GoogleCredential.fromStream(GoogleService.class.getResourceAsStream("/bm-manager-client.json"))
                .createScoped(SCOPES);*/

        /*HTTP_TRANSPORT = new NetHttpTransport();
        GoogleCredential credential = null;

        try {
            credential = new GoogleCredential.Builder()
                    .setTransport(HTTP_TRANSPORT)
                    .setJsonFactory(JSON_FACTORY)
                    .setServiceAccountId("bachelor-manager@bm-manager.iam.gserviceaccount.com")
                    .setServiceAccountScopes(SCOPES)
                    .setServiceAccountPrivateKeyFromP12File(new File(System.getProperty("user.home"), ".credentials/bm-manager-client.p12"))
                    .build();
        }catch (GeneralSecurityException gse) {
            gse.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }*/



        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");
        return credential;
    }

    /**
     * Build and return an authorized Gmail client service.
     * @return an authorized Gmail client service
     * @throws IOException
     */
    public static Gmail getGmailService() throws IOException {
        Credential credential = authorize();
        return new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static Drive getDriveService() throws IOException {
        Credential credential = authorize();
        return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}
