package vasilkov.labbpls2.PublisherConsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class RabbitAmqpRunner implements CommandLineRunner {

    @Autowired
    private ConfigurableApplicationContext ctx;


    @Override
    public void run(String... args) throws Exception {
        System.out.println("Ready, started running");
        Thread.sleep(10000);
        ctx.close();
    }
}
