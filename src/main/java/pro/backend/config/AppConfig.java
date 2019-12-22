package pro.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
//@PropertySource("classpath:db.properties")
@EnableTransactionManagement
public class AppConfig {

    @Autowired
    private Environment env;
}
