package vn.techmaster.finalproject.service.serviceimplement;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import vn.techmaster.finalproject.service.serviceinterface.MailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
@Component
@AllArgsConstructor
public class MailServiceImpl implements MailService {


    private final JavaMailSender sender;

    @Async
    public void sendEmail(String email, String subject, String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
        // Send Message!
        sender.send(message);
    }
}
