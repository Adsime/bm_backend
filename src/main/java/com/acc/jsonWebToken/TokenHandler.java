package com.acc.jsonWebToken;

import com.acc.models.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jdk.nashorn.internal.parser.Token;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Created by melsom.adrian on 29.03.2017.
 */
public class TokenHandler {

    public static final long DAY = 1000L * 60L * 60L * 24L;

    public TokenHandler() {

    }

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            Date date = new Date();
            date.setTime(System.currentTimeMillis() + (90L * DAY));
            String token = JWT.create()
                    .withExpiresAt(date)
                    .sign(algorithm);
            return token;
        } catch (UnsupportedEncodingException exception){
            return null;
        } catch (JWTCreationException exception){
            return null;
        }
    }

    public boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            System.out.println(jwt.getExpiresAt());
            return true;
        } catch (UnsupportedEncodingException exception){
            return false;
        } catch (JWTVerificationException jve) {
            return false;
        }
    }
}
