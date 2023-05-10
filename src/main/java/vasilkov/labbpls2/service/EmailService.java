package vasilkov.labbpls2.service;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailService {


    @Bean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setProtocol("SMTP");
        javaMailSender.setHost("smtp.yandex.ru");
        javaMailSender.setPort(587);
        javaMailSender.setPassword("Derftion011");
        javaMailSender.setUsername("vasilkov.a.s@yandex.ru");
        return javaMailSender;
    }

    public void sendSimpleMessage(String to, String subject, String text, JavaMailSenderImpl mailSender) {
        SimpleMailMessage message = new SimpleMailMessage();
        String email = "vasilkov.a.s@yandex.ru";
        message.setFrom(email);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}