package vasilkov.labbpls2.service;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@RabbitListener(queues = "ordersId")
@Service
public class EmailService {


    //    @Bean
//    public JavaMailSenderImpl mailSender() {
//        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
//        javaMailSender.setProtocol("smtp");
//        javaMailSender.setHost("smtp.yandex.ru");
//        javaMailSender.setPort(587);
//        javaMailSender.setUsername("vasilkov.a.s@yandex.ru");
//        javaMailSender.setPassword("");
//        return javaMailSender;
//    }
    @Autowired
    private JavaMailSender emailSender;

    final
    JavaMailSenderImpl javaMailSender;

    public EmailService(JavaMailSenderImpl javaMailSender) {
        this.javaMailSender = javaMailSender;
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

    @RabbitHandler
    public void receiveOrderId(String idAsString) {
        sendSimpleMessage(
                "vasilkov.a.s@yandex.ru",
                "Заказ(" + idAsString +") на обработку",
                "Проверьте данные заказа: " + idAsString,
                javaMailSender);
    }


}