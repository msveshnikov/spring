package batch.lessons;

import batch.lessons.services.BeanWithDependency;
import batch.lessons.services.interfaces.Calculator;
import batch.lessons.services.interfaces.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by max on 02.09.16.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
public class AnotherConfiguration {

    @Autowired
    GreetingService greetingService;
    @Autowired
    Calculator calc;

    @Bean
    BeanWithDependency beanWithDependency() {
        return new BeanWithDependency(greetingService, calc);
    }
}
