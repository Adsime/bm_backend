package com.acc.google;

/**
 * Created by melsom.adrian on 08.03.2017.
 */

import com.acc.jsonWebToken.TokenHandler;
import com.acc.models.Token;
import com.acc.models.User;
import java.io.IOException;
 
public class DriveApi {
    public static void main(String[] args) throws IOException {
        User user = new User("Adrian", "Melsom", "ad@ad.ad",
                "99999999", "adrian.melsom", "0", null);
        Token token = new TokenHandler().generateToken(user);
        new TokenHandler().verify(token.getToken());
    }
}
