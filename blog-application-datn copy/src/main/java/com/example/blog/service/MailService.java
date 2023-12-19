package com.example.blog.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

//    public MailService(JavaMailSender javaMailSender) {
//        this.mailSender = javaMailSender;
//
//    }


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

    public void sendEmailWithTemplate(String to, String subject, Map<String, Object> model, String templateName) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("VnNews@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);

            String content = templateEngine.process(templateName, new Context(Locale.getDefault(), model));
            helper.setText(content, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    // TODO: Gửi email xác nhận tài khoản
    public void sendEmailConfirmAccountRegister(String toUser, Map<String, Object> data) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("VnNews@gmail.com");
            helper.setTo(toUser);
            helper.setSubject("Xác nhận đăng ký tài khoản");

            Context context = new Context();
            context.setVariable("username", data.get("username"));
            context.setVariable("linkConfirm", data.get("linkConfirm"));

            String htmlContent = templateEngine.process("mail/confirmation-account-register", context);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("Error when sending email confirm register account: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    // TODO: Gửi email đổi mật khẩu
    public void sendEmailChangePassword(String toUser, Map<String, Object> data) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("VnNews@gmail.com");
            helper.setTo(toUser);
            helper.setSubject("Xác nhận đổi mật khẩu");

            Context context = new Context();
            context.setVariable("username", data.get("username"));
            context.setVariable("linkConfirm", data.get("linkConfirm"));

            String htmlContent = templateEngine.process("mail/reset-password", new Context(Locale.getDefault(), data));
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("Error when sending email reset password: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
