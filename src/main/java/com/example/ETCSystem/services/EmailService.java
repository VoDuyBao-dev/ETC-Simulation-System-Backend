package com.example.ETCSystem.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service

public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Autowired
    public EmailService(JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendOtpEmail(String to, String otp) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(to);
        helper.setSubject("Your OTP Code");
//        from thì tự động lấy trong câú hình

//        Chuyển đổi OTP qua bên thymeleaf(HTML)
        Context context = new Context();
        context.setVariable("otp", otp);
        context.setVariable("appName", "ETC System");

//        Render HTML template
        String htmlContent = templateEngine.process("email/otp_email", context);
        helper.setText(htmlContent, true); // true để gửi dưới dạng HTML

        mailSender.send(mimeMessage);
    }
}
