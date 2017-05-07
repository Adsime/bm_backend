package com.acc.jsonWebToken;

import com.acc.database.repository.AccountRepository;
import com.acc.database.repository.UserRepository;
import com.acc.database.specification.GetUserByEIdSpec;
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
import javax.ws.rs.container.ContainerRequestContext;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by melsom.adrian on 29.03.2017.
 */
public class TokenHandler {

    public static final long DAY = 1000L * 60L * 60L * 24L;
    public static final long HOUR = DAY/24;
    public static final long HALF_HOUR = HOUR / 2;

    public static final String USER_ACCESS_LEVEL = "UAL";
    public static final String USER = "USER";

    @Inject
    private ContextUser contextUser;

    @Inject
    private UserRepository repo;

    public TokenHandler() {

    }

    public Map<String, Object> createHeaders(User user) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("alg", "HS256");
        headers.put("typ", "JWT");
        return headers;
    }

    /**
     * Returns a token lasting an extended period of time
     * @param user User
     * @return Token
     */
    public Token generateAccessToken(User user) {
        return generateToken(user, DAY * 90);
    }

    /**
     * Returns a token lasting a short period of time
     * @param user User
     * @return Token
     */
    public Token generateRefreshToken(User user) {
        return generateToken(user, HALF_HOUR);
    }

    /**
     * Responsible for generating tokens
     * @param user User
     * @param timeExtention long
     * @return Token
     */
    private Token generateToken(User user, long timeExtention) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            Date date = new Date();
            date.setTime(System.currentTimeMillis() + timeExtention);
            String token = JWT.create()
                    .withExpiresAt(date)
                    .withClaim(USER_ACCESS_LEVEL, user.getAccessLevel())
                    .withClaim(USER, user.getEnterpriseID())
                    .withHeader(createHeaders(user))
                    .sign(algorithm);
            return new Token(token);
        } catch (UnsupportedEncodingException | JWTCreationException exception) {
            return null;
        }
    }

    /**
     * Determines the validity of a given token. Will set the security context user
     * if the token is
     * @param token Token
     * @param context ContainerRequestContent
     * @return boolean
     */
    @SuppressWarnings("all")
    public boolean verify(String token, ContainerRequestContext context) {
        try {
            DecodedJWT jwt = decode(token);
            Date expiresAt = jwt.getExpiresAt();
            User user = repo.getQuery(new GetUserByEIdSpec(jwt.getClaim(USER).toString())).get(0);
            if (expiresAt.after(new Date())) {
                getUserAllowance(user, context);
                return true;
            }
        } catch (Exception e) {

        }
        return false;
    }

    /**
     * Decodes a given token and returns an object containing its components.
     * @param token Token
     * @return DecodedJWT
     */
    @SuppressWarnings("all")
    private DecodedJWT decode(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm).build();
            return verifier.verify(token);

        } catch (UnsupportedEncodingException | JWTVerificationException exception) {

        }
        return null;
    }

    /**
     * Determines the client's role within the API.
     * @param user User
     * @param context ContainerRequestContext
     * @throws Exception To ensure any errors results in the client not getting access.
     */
    private void getUserAllowance(User user, ContainerRequestContext context) throws Exception {
        contextUser.setName(user.getEnterpriseID());
        switch (Integer.parseInt(user.getAccessLevel())) {
            case BMSecurityContext.ADMIN: {
                contextUser.setRole("admin");
                break;
            }
            case BMSecurityContext.SUPERVISOR: {
                contextUser.setRole("supervisor");
                break;
            }
            case BMSecurityContext.USER: {
                contextUser.setRole("user");
                break;
            }
            case BMSecurityContext.STUDENT: {
                contextUser.setRole("student");
                break;
            }
        }
        context.setSecurityContext(new BMSecurityContext(contextUser));
    }
}
