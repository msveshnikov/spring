package batch;

import lessons.LessonsConfiguration;
import lessons.services.BeanWithDependency;
import lessons.services.interfaces.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(LessonsConfiguration.class);
        context.refresh();

        context.registerShutdownHook();
        Environment env = context.getEnvironment();
        boolean containsFoo = env.containsProperty("foo");
        System.out.println("Does my environment contain the 'foo' property? " + containsFoo);

        boolean containsDbUrl = env.containsProperty("db.url");
        System.out.println("Does my environment contain the 'db.url' property? " + containsDbUrl);

        GreetingService greetingService = (GreetingService) context.getBean("greetingService");
        logger.info(greetingService.sayGreeting());  // "Greeting, user!"
        GreetingService greetingService2 = (GreetingService) context.getBean("greetingService");
        logger.info(greetingService2.sayGreeting());  // "Greeting, user!"

        BeanWithDependency withDependency = context.getBean(BeanWithDependency.class);
        logger.info(withDependency.printText().toString());

    }
}