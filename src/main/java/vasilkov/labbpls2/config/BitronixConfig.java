package vasilkov.labbpls2.config;

import bitronix.tm.BitronixTransactionManager;
import bitronix.tm.TransactionManagerServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BitronixConfig {
    @Bean(destroyMethod = "shutdown")
    public BitronixTransactionManager bitronixTransactionManager() {
        return TransactionManagerServices.getTransactionManager();
    }
}