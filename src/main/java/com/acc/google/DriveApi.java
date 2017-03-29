package com.acc.google;

/**
 * Created by melsom.adrian on 08.03.2017.
 */

import com.acc.models.Problem;
import com.acc.service.ProblemService;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.drive.model.File;
import org.apache.http.HttpConnection;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.inject.Inject;
import javax.servlet.Servlet;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
 
public class DriveApi {
    public static void main(String[] args) throws IOException {
        // Build a new authorized API client service.

        //FileHandler fileHandler = new FileHandler();
        //System.out.println(fileHandler.deleteFile("1ehn6el3kq_ZKCc990bP2XVOqy9elyPC_zcoRgBAXz1Q"));
        //System.out.println(fileHandler.createFile("Dette er en test", "Og dette er content :)", Arrays.asList("0ByI1HjM5emiFdElMS0p4Y0pDVFU")));
        //System.out.println(fileHandler.getFolder("0ByI1HjM5emiFdElMS0p4Y0pDVFU"));
        //System.out.println(fileHandler.updateFile("1q5hVw6wTvFGv1FFyhWzzVZ0z3_6dkZxatDuC6QARCTg", "Test", ""));
        /*if (files == null || files.size() == 0) {

        }*/
        //System.out.println(fileHandler.getTreeStructure());
        //System.out.println(fileHandler.asdasd("17_tGxvUKmWK_VTgXPQg-QDEa918GTj2Rje-bDV1SX7o").toString());


        /*Credential credential = fileHandler.authorize();
        System.out.println(credential.getAccessToken());
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet("https://docs.google.com/spreadsheets/d/17_tGxvUKmWK_VTgXPQg-QDEa918GTj2Rje-bDV1SX7o/edit?usp=drivesdk");
        get.setHeader("Authorization", "Bearer ya29.GlwYBBiYMg-2_uqtrgS7_U2ironLwK-4JGzs_QMR32MVz-Y5phPBWPYfl5R0jVUXhgRzGvtIsNXGpq6AXVERA_GNm6M6E0W56tdFkKG6vhzEpGBlImPeWzI3bf-Nyw");
        HttpResponse response = client.execute(get);
        */


    }
}
