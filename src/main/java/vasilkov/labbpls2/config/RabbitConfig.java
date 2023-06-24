package vasilkov.labbpls2.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("amqp-profile")
@Configuration
public class RabbitConfig {

    @Bean
    public Queue ordersIdQueue() {
        return new Queue("ordersId");
    }

}
