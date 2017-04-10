package com.acc.google;

/**
 * Created by melsom.adrian on 08.03.2017.
 */

import com.acc.jsonWebToken.TokenHandler;
import com.acc.models.Token;
import com.acc.models.User;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.drive.Drive;

import java.io.IOException;
 
public class DriveApi {
    public static void main(String[] args) throws IOException {
        User user = new User("Adrian", "Melsom", "ad@ad.ad",
                "99999999", "adrian.melsom", "0", null);
        FileHandler fileHandler = new FileHandler();
        Credential credential = fileHandler.authorize();
        //User user = new User("Adrian", "Melsom", "ad@ad.ad", "99999999", "adrian.melsom", "0", null);
        /*User user = new User("Adrian", "Melsom", "ad@ad.ad", "adrian.melsom", "0", null);
        Token token = new TokenHandler().generateToken(user);
        new TokenHandler().verify(token.getToken());*/

    }
}
