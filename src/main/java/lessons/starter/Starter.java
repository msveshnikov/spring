package lessons.starter;

import lessons.LessonsConfiguration;
import lessons.services.BeanWithDependency;
import lessons.services.interfaces.GreetingService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

/**
 * Created by max on 02.09.16.
 */
public class Starter {
    private static final Logger logger = LogManager.getLogger(Starter.class);

    public static void main(String[] args) {
        logger.info("Starting configuration...");

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        //context.getEnvironment().setActiveProfiles("production");
        context.register(LessonsConfiguration.class);
        context.refresh();

        context.registerShutdownHook();
        Environment env = context.getEnvironment();
        boolean containsFoo = env.containsProperty("foo");
        System.out.println("Does my environment contain the 'foo' property? " + containsFoo);

        GreetingService greetingService = (GreetingService) context.getBean("greetingService");
        logger.info(greetingService.sayGreeting());  // "Greeting, user!"
        GreetingService greetingService2 = (GreetingService) context.getBean("greetingService");
        logger.info(greetingService2.sayGreeting());  // "Greeting, user!"

        BeanWithDependency withDependency = context.getBean(BeanWithDependency.class);
        logger.info(withDependency.printText());

//        java.lang.Object auto=context.getBean("auto");

//        System.exit(SpringApplication.exit(SpringApplication.run(
//                BatchConfiguration.class, args)));

        logger.info("App finishing");
    }
}
