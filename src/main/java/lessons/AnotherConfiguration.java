package lessons;

import lessons.services.BeanWithDependency;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by max on 02.09.16.
 */
@Configuration
public class AnotherConfiguration {
    @Bean
    BeanWithDependency beanWithDependency() {
        return new BeanWithDependency();
    }
}
