package com.example.blog.service;

import com.example.blog.dto.BlogSendMailDto;
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
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;


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

            String htmlContent = templateEngine.process("mail/reset-password", context);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("Error when sending email reset password: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    // TODO: Gửi email trả lời phản hồi, góp ý của người dùng
    public void sendEmailReplyUserFeedback(String toUser, Map<String, Object> data) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("VnNews@gmail.com");
            helper.setTo(toUser);
            helper.setSubject("Trả lời đánh giá, góp ý của người dùng");

            Context context = new Context();
            context.setVariable("username", data.get("username"));
            context.setVariable("contentReply", data.get("contentReply"));

            String htmlContent = templateEngine.process("mail/feedback", context);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("Error when sending email reset password: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    // TODO: Gửi mail danh sách tin tư mới cho user
    public void sendEmailToUserReceiveNews(String toUser, String username, List<BlogSendMailDto> blogs) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("VnNews@gmail.com");
            helper.setTo(toUser);
            helper.setSubject("Tin tức mới trong hệ thống.");

            Context context = new Context();
            context.setVariable("blogList", blogs);
            context.setVariable("username", username);

            String htmlContent = templateEngine.process("mail/receive-news", context);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("Error when sending email reset password: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
