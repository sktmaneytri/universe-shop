package com.hcmute.ecommerce.universeshop.base.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
@Service
@Slf4j
public class EmailService {
    private final JavaMailSender javaMailSender;
    @Value("20110422@student.hcmute.edu.vn")
    private String email;
    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    @Autowired
    private ThymeleafService thymeleafService;
    public void sendEmail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        javaMailSender.send(message);
    }

    public String loadEmailTemplate() {
        try {
            URL resource = getClass().getClassLoader().getResource("templates/email.html");
            if (resource != null) {
                Path path = Paths.get(resource.toURI());
                return Files.readString(path);
            } else {
                // Handle the case where the resource is not found
                System.err.println("Email template resource not found!");
                return "";
            }
        } catch (Exception e) {
            // Handle other exceptions (e.g., log them)
            e.printStackTrace();
            return "";
        }
    }

    public String populateEmailTemplate(String template, String code) {
        // Example: Replace ${name} and ${content} in the template with actual values
        return template
                .replace("${code}", code);
    }
    public void sendVerificationCode(String to, String verificationCode) {
        String emailTemplate = loadEmailTemplate();

        // Modify the template with the verification code
        String populatedTemplate = populateEmailTemplate(emailTemplate, verificationCode);

        // Send the email
        sendEmail(to, "Verification Code", populatedTemplate);
    }
    public void sendEmail(String email, Map<String, Object> variables, String subject) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            message.setRecipients(MimeMessage.RecipientType.TO, email);
            message.setSubject(subject);
            message.setContent(thymeleafService.createContentGetOTP("email.html", variables), "text/html; charset=utf-8");
            javaMailSender.send(message);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
