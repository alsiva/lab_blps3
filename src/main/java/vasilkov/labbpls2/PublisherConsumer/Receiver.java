package vasilkov.labbpls2.PublisherConsumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import vasilkov.labbpls2.service.EmailService;

@RabbitListener(queues = "ordersId")
public class Receiver {

    @Autowired
    private EmailService emailService;

    @RabbitHandler
    public void receiveOrderId(String idAsString) {
        System.out.println("Received order with id: " + idAsString);
        emailService.sendSimpleMessage(
                "alexjethohma@gmail.com",
                "Заказ(" + idAsString +") на обработку",
                "Проверьте данные заказа: " + idAsString,
                emailService.javaMailSender);
    }
}
