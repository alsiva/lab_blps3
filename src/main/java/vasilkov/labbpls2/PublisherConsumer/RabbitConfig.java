package vasilkov.labbpls2.PublisherConsumer;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"amqp"})
@Configuration
public class RabbitConfig {

    @Bean
    public Queue ordersIdQueue() {
        return new Queue("ordersId");
    }

    @Profile("receiver")
    @Bean
    public Receiver receiver() {
        return new Receiver();
    }

    @Profile("sender")
    @Bean
    public Sender sender() {
        return new Sender();
    }

}
