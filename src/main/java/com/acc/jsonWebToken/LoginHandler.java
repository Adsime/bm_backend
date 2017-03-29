package com.acc.jsonWebToken;

import com.acc.models.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import sun.rmi.runtime.Log;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * Created by melsom.adrian on 29.03.2017.
 */
public class LoginHandler {

    public LoginHandler() {

    }


    public User verify(String credentials) {
        try {
            credentials = credentials.substring("Basic ".length()).trim();
            credentials = new String(Base64.getDecoder().decode(credentials));
            User user = new User();
            user.setAccessLevel("9");
            user.setFirstName("Admin");
            user.setLastName("Admin");
            user.setEnterpriseID("admin.admin");
            user.setEmail("admin@admin.admin");
            String pw = credentials.split(":")[1];
            String un = credentials.split(":")[0];
            if(pw.equals("admin") && un.equals(user.getEnterpriseID())) {
                return user;
            }
        } catch (Exception e) {

        }
        return null;
    }
}
