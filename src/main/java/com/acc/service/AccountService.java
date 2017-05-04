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

import javax.inject.Inject;
import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
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

    public User verifyUser(String encodedCreds) {
        try {
            Credentials credentials = new Credentials(encodedCreds);
            return repo.matchPassword(credentials.getUsername(), credentials.getPassword());
        } catch (IllegalArgumentException |EntityNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Token getToken(User user) {
        return tokenHandler.generateAccessToken(user);
    }

    public void resetPassword(long id) {
        User user = userRepo.getQuery(new GetUserByIdSpec(id)).get(0);
        Token token = tokenHandler.generateResetToken(user);
        try {
            MimeMessage message = mailHandler.createEmail("melsom.adrian@accenture.com",
                    "potasian17@accenture.com",
                    "Password reset",
                    "<h1>Password reset - Bachelor manager</h1>" +
                            "</br></br>" +
                            "<p>Trykk på følgende link for å sette ditt nye passord:</p>" +
                            "<p><a href=\"" + GoogleService.applicationPath + Coder.encode(token.getToken()) +  "\">Linken</a> er aktiv i 30 minutter</p>");
            mailHandler.sendMessage("potasian17@gmail.com", message);
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
        System.out.println("done");
    }
}
