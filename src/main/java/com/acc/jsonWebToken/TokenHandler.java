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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by melsom.adrian on 29.03.2017.
 */
public class TokenHandler {

    public static final long DAY = 1000L * 60L * 60L * 24L;

    public TokenHandler() {

    }

    public Map<String, Object> createHeaders(User user) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("user", user.getEnterpriseID());
        headers.put("alg", "HS256");
        headers.put("typ", "JWT");
        return headers;
    }

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            Date date = new Date();
            date.setTime(System.currentTimeMillis() + (90L * DAY));
            String token = JWT.create()
                    .withExpiresAt(date)
                    .withHeader(createHeaders(user))
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
            Date expiresAt = jwt.getExpiresAt();
            if(expiresAt.after(new Date())) {
                return true;
            }
        } catch (UnsupportedEncodingException exception){

        } catch (JWTVerificationException jve) {

        }
        return false;
    }
}
