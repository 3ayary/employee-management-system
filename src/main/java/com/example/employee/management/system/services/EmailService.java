package com.example.employee.management.system.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.mailSender = javaMailSender;
    }

    @Value("${backend.origin}")
    private String ORIGIN;
    @Value("${spring.mail.username}")
    private String from;

    public void sendAccountEmail(String to, String token) {
        String link = ORIGIN + "?token=" + token;
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setTo(to);
        message.setSubject("Create your account!");
        message.setText("Hi, Please Create your account using this link below  \n " + link);
        mailSender.send(message);

    }

}
