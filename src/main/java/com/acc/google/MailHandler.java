package com.acc.google;

/**
 * Created by melsom.adrian on 29.04.2017.
 */
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static com.acc.google.GoogleService.getGmailService;

public class MailHandler {

    private static Logger LOGGER = Logger.getLogger("application");

    /**
     * Send an email from the user's mailbox to its recipient.
     *
     * @param userId User's email address. The special value "me"
     * can be used to indicate the authenticated user.
     * @param email Email to be sent.
     * @throws MessagingException to be handled at method call
     * @throws IOException to be handled at method call
     */
    public void sendMessage(String userId, MimeMessage email)
            throws MessagingException, IOException {
        Gmail service = getGmailService();
        Message message = createMessageWithEmail(email);
        service.users()
                .messages()
                .send(userId, message)
                .execute();
        LOGGER.info("Password reset sent to user with id: " + userId);
    }

    /**
     * Create a Message from an email
     *
     * @param email Email to be set to raw of message
     * @return Message containing base64url encoded email.
     * @throws IOException to be handled at method call
     * @throws MessagingException to be handled at method call
     */
    private Message createMessageWithEmail(MimeMessage email)
            throws MessagingException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        email.writeTo(baos);
        String encodedEmail = Base64.encodeBase64URLSafeString(baos.toByteArray());
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }

    /**
     * Builds a MimeMessage object
     *
     * @param to String with receiver address
     * @param from String with sender address
     * @param subject String containing the email subject
     * @param bodyText String containing the email body in html format
     * @return MimeMessage
     * @throws MessagingException to be handled at method call
     */
    public MimeMessage createEmail(String to,
                                          String from,
                                          String subject,
                                          String bodyText)
            throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(to));
        email.setSubject(subject);
        email.setContent(bodyText, "text/html; charset=utf-8");
        return email;
    }
}
