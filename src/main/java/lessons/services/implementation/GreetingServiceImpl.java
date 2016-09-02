package lessons.services.implementation;

import lessons.services.interfaces.Calculator;
import lessons.services.interfaces.GreetingService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by max on 02.09.16.
 */

@Component("greetingService")
//@Scope("prototype")
public class GreetingServiceImpl implements GreetingService {
    private static final Logger logger = LogManager.getLogger(GreetingServiceImpl.class);
    private ApplicationContext context;
    private Calculator calculator;

    public GreetingServiceImpl() {
        logger.info("Creating GreetingServiceImpl");
    }

    @Required
    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    @Required
    @Autowired
    public void setCalculator(Calculator calculator) {
        this.calculator = calculator;
    }

    @PostConstruct
    private void postConstruct() {
        logger.info("postConstruct");
    }

    @PreDestroy
    private void preDestroy() {
        logger.info("preDestroy");
    }

    @Override
    public String sayGreeting() {
        logger.info("Calculate 2+2=" + calculator.add(2, 2));
        return "Greeting, user!";
    }
}