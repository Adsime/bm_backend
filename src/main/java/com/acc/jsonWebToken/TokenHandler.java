package com.acc.jsonWebToken;

import com.acc.models.Token;
import com.acc.models.User;
import com.acc.requestContext.BMSecurityContext;
import com.acc.requestContext.ContextUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by melsom.adrian on 29.03.2017.
 */
public class TokenHandler {

    public static final long DAY = 1000L * 60L * 60L * 24L;

    public static final String USER_ACCESS_LEVEL = "UAL";
    public static final String USER = "USER";

    @Inject
    private ContextUser user;

    public TokenHandler() {

    }

    public Map<String, Object> createHeaders(User user) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("alg", "HS256");
        headers.put("typ", "JWT");
        return headers;
    }

    public Token generateAccessToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            Date date = new Date();
            date.setTime(System.currentTimeMillis() + (90L * DAY));
            String token = JWT.create()
                    .withExpiresAt(date)
                    .withClaim(USER_ACCESS_LEVEL, user.getAccessLevel())
                    .withClaim(USER, user.getEnterpriseID())
                    .withHeader(createHeaders(user))
                    .sign(algorithm);
            return new Token(token);
        } catch (UnsupportedEncodingException exception) {
            return null;
        } catch (JWTCreationException exception) {
            return null;
        }
    }

    public Token generateResetToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            Date date = new Date();
            date.setTime(System.currentTimeMillis() + ((1/24) * DAY));
            String token = JWT.create()
                    .withExpiresAt(date)
                    .withClaim(USER_ACCESS_LEVEL, user.getAccessLevel())
                    .withClaim(USER, user.getEnterpriseID())
                    .withHeader(createHeaders(user))
                    .sign(algorithm);
            return new Token(token);
        } catch (UnsupportedEncodingException exception) {
            return null;
        } catch (JWTCreationException exception) {
            return null;
        }
    }

    public ContextUser getUserAllowance(String token) {
        DecodedJWT jwt = decode(token);
        Claim ual = jwt.getClaim(USER_ACCESS_LEVEL);
        Claim un = jwt.getClaim(USER);
        int accessLevel = Integer.parseInt(ual.asString());
        user.setName(un.asString());
        switch (accessLevel) {
            case BMSecurityContext.ADMIN: {
                user.setRole("admin");
                break;
            }
            case BMSecurityContext.SUPERVISOR: {
                user.setRole("supervisor");
                break;
            }
            case BMSecurityContext.USER: {
                user.setRole("user");
                break;
            }
            case BMSecurityContext.STUDENT: {
                user.setRole("student");
                break;
            }
        }
        return user;
    }

    public boolean verify(String token) {
        DecodedJWT jwt = decode(token);
        Date expiresAt = jwt.getExpiresAt();
        if (expiresAt.after(new Date())) {
            return true;
        }
        return false;
    }

    private DecodedJWT decode(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt;
        } catch (UnsupportedEncodingException exception) {

        } catch (JWTVerificationException jve) {

        }
        return null;

    }
}
