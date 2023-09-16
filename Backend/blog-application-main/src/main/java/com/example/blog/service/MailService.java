package com.example.blog.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;

    public void sendMail(String receiver, String subject, String content) {
        // Create a Simple MailMessage.
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("VnNews@gmail.com");
        message.setTo(receiver);
        message.setSubject(subject);
        message.setText(content);

        // Send Message!
        mailSender.send(message);
    }
}