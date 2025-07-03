package com.example.Notification_Service.Service;

import com.example.Notification_Service.Model.NotificationMessage;
import jakarta.mail.internet.MimeMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class NotificationService {

    private final JavaMailSender mailSender;

    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    @Bean
    public Consumer<NotificationMessage> notificationConsumer() {


        return message -> {
            System.out.println("Received message: " + message.getEmail());
            try {
                MimeMessage messagecreater = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(messagecreater, true, "UTF-8");

                helper.setFrom("sarbudeen8124@gmail.com", "Online Learning Platform");
                SimpleMailMessage mail = new SimpleMailMessage();
                mail.setTo(message.getEmail());
                mail.setSubject(message.getSubject());
                mail.setText(message.getBody());
                mailSender.send(mail);
                System.out.println("Email sent to: " + message.getEmail());
            } catch (Exception e) {
                System.out.println("❌ Email sending failed:");
                e.printStackTrace();
            }
        };
    }
}
