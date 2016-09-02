package lessons;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Конфигурационный класс Spring IoC контейнера
 */
@Configuration
@ComponentScan
@Import(AnotherConfiguration.class)
public class LessonsConfiguration {

//    @Bean
//    @Scope("prototype")
//    @Description("Текстовое описание бина greetingService")
//    GreetingService greetingService() {
//        return new GreetingServiceImpl();
//    }

}
