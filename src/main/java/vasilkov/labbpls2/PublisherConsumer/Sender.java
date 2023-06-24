package vasilkov.labbpls2.PublisherConsumer;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import vasilkov.labbpls2.repository.OrderRepository;

import java.util.concurrent.TimeUnit;

public class Sender {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue queue;

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void sendToQueue() {
        Integer orderId = orderRepository.findOrderByStatusIsNull().getId();
        String orderIdAsString = Integer.toString(orderId);
        this.template.convertAndSend(queue.getName(), orderIdAsString);
        System.out.println("Sending order with id: " + orderIdAsString);
    }
}
