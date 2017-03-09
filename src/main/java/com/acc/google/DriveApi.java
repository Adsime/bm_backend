package com.acc.google;

/**
 * Created by melsom.adrian on 08.03.2017.
 */

import java.io.IOException;

public class DriveApi {
    public static void main(String[] args) throws IOException {
        // Build a new authorized API client service.

        FileHandler fileHandler = new FileHandler();
        //List<File> files = fileHandler.getFolder("0ByI1HjM5emiFdElMS0p4Y0pDVFU");
        fileHandler.updateFile("1q5hVw6wTvFGv1FFyhWzzVZ0z3_6dkZxatDuC6QARCTg", "Test", "TEEEEEEEEEEEEEEST");
        /*if (files == null || files.size() == 0) {
            System.out.println("No files found.");
        }
        else {
            System.out.println("Files:");
            System.out.println(files);
        }*/
    }


}
