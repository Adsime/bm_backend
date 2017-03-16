package com.acc.google;

/**
 * Created by melsom.adrian on 08.03.2017.
 */

import com.acc.models.Problem;
import com.acc.service.ProblemService;
import com.google.api.services.drive.model.File;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
 
public class DriveApi {
    public static void main(String[] args) throws IOException {
        // Build a new authorized API client service.

        FileHandler fileHandler = new FileHandler();
        System.out.println(fileHandler.deleteFile("1ehn6el3kq_ZKCc990bP2XVOqy9elyPC_zcoRgBAXz1Q"));
        //System.out.println(fileHandler.createFile("Dette er en test", "Og dette er content :)", Arrays.asList("0ByI1HjM5emiFdElMS0p4Y0pDVFU")));
        //List<File> files = fileHandler.getFolder("0ByI1HjM5emiFdElMS0p4Y0pDVFU");
        //System.out.println(fileHandler.updateFile("1q5hVw6wTvFGv1FFyhWzzVZ0z3_6dkZxatDuC6QARCTg", "Test", ""));
        /*if (files == null || files.size() == 0) {

        }*/
    }
}
