package vibeville.app.service;

import javax.mail.MessagingException;

public interface EmailService {

    boolean sendSimpleMessage(String to) throws MessagingException;

    void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) throws MessagingException;
}
