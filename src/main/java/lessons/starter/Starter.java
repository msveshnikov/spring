package lessons.starter;

import lessons.LessonsConfiguration;
import lessons.services.BeanWithDependency;
import lessons.services.interfaces.GreetingService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * Created by max on 02.09.16.
 */
public class Starter {
    private static final Logger logger = LogManager.getLogger(Starter.class);

    public static void main(String[] args) {
        logger.info("Starting configuration...");

        AbstractApplicationContext context = new AnnotationConfigApplicationContext(LessonsConfiguration.class);
        context.registerShutdownHook();

        GreetingService greetingService = (GreetingService) context.getBean("greetingService");
        logger.info(greetingService.sayGreeting());  // "Greeting, user!"
        GreetingService greetingService2 = (GreetingService) context.getBean("greetingService");
        logger.info(greetingService2.sayGreeting());  // "Greeting, user!"

        BeanWithDependency withDependency = context.getBean(BeanWithDependency.class);
        logger.info(withDependency.printText());

        logger.info("App finishing");
    }
}
