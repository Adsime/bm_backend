package com.acc.google;

/**
 * Created by melsom.adrian on 08.03.2017.
 */

import java.io.IOException;
 
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
        //System.out.println(fileHandler.asdasd("17_tGxvUKmWK_VTgXPQg-QDEa918GTj2Rje-bDV1SX7o").toString())

        FileHandler fileHandler = new FileHandler();
        MailHandler mailHandler = new MailHandler();

        //FileService service = new FileService();
        //service.setFileHandler(fileHandler);

        try {
            mailHandler.sendMessage("potasian17@gmail.com",
                    mailHandler.createEmail("melsom.adrian@accenture.com", "potasian17@gmail.com", "test", "test"));
        } catch (Exception me) {
            me.printStackTrace();
        }
        System.out.println(fileHandler.getTreeStructure());

        //service.getFileAsHtml("1DxwpUClmVaB_c4ieHS-NcELNNQ3gSU-iW1HbduZM7Dk");
        //service.getFile("1lTJHklu-hBklLkUUGxoq8eaENqT6FFMRbRNh7WjYhAM");
        //fileHandler.createFolder(new Folder("asdasd", "0ByI1HjM5emiFVlQ5RWdhTGJXVGc"));
        //System.out.println(fileHandler.getFolder(null));
        /*HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet("https://docs.google.com/spreadsheets/d/17_tGxvUKmWK_VTgXPQg-QDEa918GTj2Rje-bDV1SX7o/edit?usp=drivesdk");
        get.setHeader("Authorization", "Bearer ya29.GlwYBBiYMg-2_uqtrgS7_U2ironLwK-4JGzs_QMR32MVz-Y5phPBWPYfl5R0jVUXhgRzGvtIsNXGpq6AXVERA_GNm6M6E0W56tdFkKG6vhzEpGBlImPeWzI3bf-Nyw");
        HttpResponse response = client.execute(get);
        */
        //User user = new User("Adrian", "Melsom", "ad@ad.ad", "99999999", "adrian.melsom", "0", null);
        /*User user = new User("Adrian", "Melsom", "ad@ad.ad", "adrian.melsom", "0", null);
        Token token = new TokenHandler().generateAccessToken(user);
        new TokenHandler().verify(token.getToken());*/

    }
}
