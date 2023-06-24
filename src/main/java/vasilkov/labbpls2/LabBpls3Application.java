package vasilkov.labbpls2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import vasilkov.labbpls2.PublisherConsumer.RabbitAmqpRunner;

@SpringBootApplication
@EnableScheduling
public class LabBpls3Application {

    @Profile("usage_message")
    @Bean
    public CommandLineRunner usage() {
        return args -> {
            System.out.println("This app uses Spring Profiles to control its behavior.\n");
            System.out.println("Sample usage: java -jar rabbit-tutorials.jar --spring.profiles.active=hello-world,sender");
        };
    }

    @Profile("!usage_message")
    @Bean
    public CommandLineRunner amqpRunner() {
        return new RabbitAmqpRunner();
    }

    public static void main(String[] args) {
        SpringApplication.run(LabBpls3Application.class, args);
    }

}
