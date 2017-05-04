package com.acc.service;

import com.acc.database.repository.AccountRepositoryImpl;
import com.acc.database.repository.UserRepository;
import com.acc.database.specification.GetUserByEIdSpec;
import com.acc.database.specification.GetUserByIdSpec;
import com.acc.google.GoogleService;
import com.acc.google.MailHandler;
import com.acc.jsonWebToken.Coder;
import com.acc.jsonWebToken.TokenHandler;
import com.acc.models.Credentials;
import com.acc.models.Token;
import com.acc.models.User;
import com.acc.requestContext.BMSecurityContext;
import com.acc.requestContext.ContextUser;
import org.eclipse.jetty.http.HttpStatus;
import sun.security.util.Resources_it;

import javax.inject.Inject;
import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.io.IOException;

/**
 * Created by melsom.adrian on 28.03.2017.
 */
public class AccountService {

    @Inject
    private AccountRepositoryImpl repo;

    @Inject UserRepository userRepo;

    @Inject
    private TokenHandler tokenHandler;

    @Inject
    private MailHandler mailHandler;

    @Context
    private ContainerRequestContext context;

    public void setMailHandler(MailHandler mailHandler) {
        this.mailHandler = mailHandler;
    }

    public void setUserRepo(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User verifyUser(String encodedCreds) {
        try {
            Credentials credentials = new Credentials(encodedCreds);
            User user = repo.matchPassword(credentials.getUsername(), credentials.getPassword());
            return user;
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
        } catch (EntityNotFoundException enfe) {
            enfe.printStackTrace();
        }
        return null;
    }

    public Token getToken(User user) {
        return tokenHandler.generateAccessToken(user);
    }

    public Response setNewPassword(String password) {
        String content;
        int status;
        try {
            ContextUser contextUser = ((BMSecurityContext)context.getSecurityContext()).getUser();
            User user = userRepo.getQuery(new GetUserByEIdSpec(contextUser.getName())).get(0);
            repo.register(user.getEnterpriseID(), password, user);
            content = "Passord endret.";
            status = HttpStatus.OK_200;
        } catch (EntityNotFoundException enfe) {
            content = enfe.getMessage();
            status = HttpStatus.BAD_REQUEST_400;
        } catch (IllegalArgumentException iae) {
            content = iae.getMessage();
            status = HttpStatus.BAD_REQUEST_400;
        }
        return Response.status(status).entity(content).build();
    }

    public Response resetPassword(long id) {
        User user = userRepo.getQuery(new GetUserByIdSpec(id)).get(0);
        Token token = tokenHandler.generateResetToken(user);
        try {
            MimeMessage message = mailHandler.createEmail(user.getEnterpriseID() + "@accenture.com",
                    "potasian17@gmail.com",
                    "Password reset",
                    "<h1>Password reset - Bachelor manager</h1>" +
                            "</br></br>" +
                            "<p>Trykk på følgende link for å sette ditt nye passord:</p>" +
                            "<p><a href=\"" + GoogleService.applicationPath + Coder.encode(token.getToken()) +  "\">Linken</a> er aktiv i 30 minutter</p>");
            mailHandler.sendMessage("potasian17@gmail.com", message);
            return Response.ok("Mail sendt!").build();
        } catch (MessagingException me) {
            me.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return Response.status(HttpStatus.SERVICE_UNAVAILABLE_503).entity("Var ikke i stand til å sende mail.").build();
    }

    public void temp(long id, String password) {
        User user = userRepo.getQuery(new GetUserByIdSpec(id)).get(0);
        repo.register(user.getEnterpriseID(), password, user);
    }
}
