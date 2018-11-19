package vibeville.app.service.impl;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import vibeville.app.service.EmailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.StringWriter;


@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    JavaMailSender emailSender;

    @Autowired
    private VelocityEngine velocityEngine;


    @Override
    public boolean sendSimpleMessage(String to) throws MessagingException {
        VelocityContext context = new VelocityContext();
        Template t = velocityEngine.getTemplate("email.vm");
        context.put("user", "www.vibeville.co.za");
        context.put("requestor", to);
        StringWriter writer = new StringWriter();
        t.merge(context, writer);
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
        message.setContent(writer.toString(), "text/html");
        helper.setTo(to);
        helper.setSubject("Registration");
        helper.setFrom("tmokhele@homeofteck.co.za");
        emailSender.send(message);
        return true;
    }

    @Override
    public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);
        FileSystemResource file
                = new FileSystemResource(new File(pathToAttachment));
        helper.addAttachment("Invoice", file);
        emailSender.send(message);
    }
}
