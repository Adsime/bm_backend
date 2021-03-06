package com.acc.service;

import com.acc.database.repository.AccountRepositoryImpl;
import com.acc.database.repository.UserRepository;
import com.acc.database.specification.GetUserAllSpec;
import com.acc.database.specification.GetUserByEIdSpec;
import com.acc.database.specification.GetUserByIdSpec;
import com.acc.google.GoogleService;
import com.acc.google.MailHandler;
import com.acc.jsonWebToken.Coder;
import com.acc.jsonWebToken.TokenHandler;
import com.acc.models.Credentials;
import com.acc.models.Token;
import com.acc.models.User;
import com.acc.providers.ConfigLoader;
import com.acc.requestContext.BMSecurityContext;
import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by melsom.adrian on 28.03.2017.
 */
public class AccountService {

    private static Logger LOGGER = Logger.getLogger("application");

    @Inject
    private AccountRepositoryImpl repo;

    @Inject UserRepository userRepo;

    @Inject
    private TokenHandler tokenHandler;

    @Inject
    private MailHandler mailHandler;

    @Context
    private ContainerRequestContext context;

    @Context
    private HttpServletRequest request;

    public void setMailHandler(MailHandler mailHandler) {
        this.mailHandler = mailHandler;
    }

    public void setUserRepo(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public Response verifyUser(String encodedCreds) {
        Credentials credentials = new Credentials(encodedCreds);
        System.out.println(credentials);
        User user;
        String ret = null;
        try {
            user = repo.matchPassword(credentials.getUsername(), credentials.getPassword());
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            LOGGER.error("Exception in AccountService.verifyUser. Trace:\n", e);
            user = initApi(credentials);
            ret = e.getMessage();
        }
        if(user == null) return Response.status(HttpStatus.NOT_FOUND_404).entity(ret).build();
        Token token = getToken(user);
        return Response.ok(token.toString()).build();
    }

    public Token getToken(User user) {
        return tokenHandler.generateAccessToken(user);
    }

    public Response setNewPassword(String password) {
        String content;
        int status;
        try {
            User user = ((BMSecurityContext)context.getSecurityContext()).getAccountUser();
            repo.register(user.getEnterpriseID(), password, user);
            content = "Passord endret.";
            status = HttpStatus.OK_200;
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            LOGGER.error("Unable to process the request. User might not exist, " +
                    "or the data is in the wrong format", e);
            content = e.getMessage();
            status = HttpStatus.BAD_REQUEST_400;
        }
        return Response.status(status).entity(content).build();
    }

    public Response resetPassword(String email) {
        try {
            String eid = email.split("@")[0];
            User user = repo.getAccount(eid);
            return resetPassword(user, -1);
        } catch (Exception e) {
            LOGGER.error("Reset password attempt made on email: "
                    + email + " while is not a registered email");
        }
        return Response.ok("Mail sendt!").build();
    }

    /**
     * Uses a predefined string of html to create an email message object
     * which is handed to MailHandler for sending.
     * @param id long
     * @return Response
     */
    public Response resetPassword(User user, long id) {
        try {
            if(user == null) {
                user = userRepo.getQuery(new GetUserByIdSpec(id)).get(0);
            }
            Token token = tokenHandler.generateRefreshToken(user);
            String url = ConfigLoader.load("appUrl");
            String mail = ConfigLoader.load("apiMail");
            MimeMessage message = mailHandler.createEmail(user.getEnterpriseID() + "@accenture.com",
                    mail,
                    "Password reset",
                    createMailBody(token, url));
            mailHandler.sendMessage(mail, message);
        }catch (EntityNotFoundException enfe) {
            LOGGER.error("Attempt to get user failed in AccountService.resetPassword", enfe);
            // No special message to retain integrity of the application. It will look as if a message was sent
        }catch (MessagingException | IOException e) {
            LOGGER.error("Unable to process the email", e);
            return Response.status(HttpStatus.SERVICE_UNAVAILABLE_503)
                    .entity("Var ikke i stand til å sende mail.")
                    .build();
        } catch (Exception e) {
            LOGGER.error("Unable to load property");
            return Response.status(HttpStatus.SERVICE_UNAVAILABLE_503)
                    .entity("Var ikke i stand til å sende mail på grunn av " +
                            "intern error. Vennligst kontakt administrator.")
                    .build();
        }
        return Response.ok("Mail sendt!").build();
    }

    private String createMailBody(Token token, String url) {
        return "<h1>Password reset - Bachelor manager</h1>" +
                "</br></br>" +
                "<p>Trykk på følgende link for å sette ditt nye passord:</p>" +
                "<p><a href=\"" + url + Coder.encode(token.getToken()) +
                "\">Linken</a> er aktiv i 30 minutter</p>";
    }

    public User initApi(Credentials credentials) {
        try {
            //Test to see if there are any users on the API
            userRepo.getQuery(new GetUserAllSpec());
            return null;
        } catch (EntityNotFoundException enfe) {
            try {
                LOGGER.info("Initializing API");
                String email = ConfigLoader.load("apiMail");
                User user = new User(credentials.getUsername(), credentials.getUsername(), email,
                        ConfigLoader.load("defaultPhone"), credentials.getUsername(), "9", Collections.emptyList());
                repo.register(user.getEnterpriseID(), credentials.getPassword(), userRepo.add(user));
                return user;
            } catch (Exception e) {
                LOGGER.error("Error initializing the API", e);
                return null;
            }
        }
    }
}
