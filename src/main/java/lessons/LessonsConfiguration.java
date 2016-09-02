package lessons;

import lessons.services.GreetingService;
import lessons.services.GreetingServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

/**
 * Конфигурационный класс Spring IoC контейнера
 */
@Configuration
public class LessonsConfiguration {

    @Bean
    @Description("Текстовое описание бина greetingService")
    GreetingService greetingService() {
        return new GreetingServiceImpl();
    }

}
