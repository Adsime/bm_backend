package com.acc.service;

import com.acc.database.repository.AccountRepositoryImpl;
import com.acc.database.repository.UserRepository;
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
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.IOException;

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

    public void setMailHandler(MailHandler mailHandler) {
        this.mailHandler = mailHandler;
    }

    public void setUserRepo(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public Response verifyUser(String encodedCreds) {
        try {
            Credentials credentials = new Credentials(encodedCreds);
            User user = repo.matchPassword(credentials.getUsername(), credentials.getPassword());
            Token token = getToken(user);
            return Response.ok(token.toString()).build();
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            LOGGER.error("Exception in AccountService.verifyUser. Trace:\n", e);
            return Response.status(HttpStatus.NOT_FOUND_404).entity(e.getMessage()).build();
        }
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

    /**
     * Uses a predefined string of html to create an email message object
     * which is handed to MailHandler for sending.
     * @param id long
     * @return Response
     */
    public Response resetPassword(long id) {
        try {
            User user = userRepo.getQuery(new GetUserByIdSpec(id)).get(0);
            Token token = tokenHandler.generateRefreshToken(user);
            MimeMessage message = mailHandler.createEmail(user.getEnterpriseID() + "@accenture.com",
                    ConfigLoader.load("apiMail"),
                    "Password reset",
                    createMailBody(token));
            mailHandler.sendMessage("potasian17@gmail.com", message);
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

    private String createMailBody(Token token) {
        return "<h1>Password reset - Bachelor manager</h1>" +
                "</br></br>" +
                "<p>Trykk på følgende link for å sette ditt nye passord:</p>" +
                "<p><a href=\"" + GoogleService.applicationPath + Coder.encode(token.getToken()) +
                "\">Linken</a> er aktiv i 30 minutter</p>";
    }
}
