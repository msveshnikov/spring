package batch.lessons.services.implementation;

import batch.lessons.services.interfaces.Calculator;
import batch.lessons.services.interfaces.GreetingService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by max on 02.09.16.
 */

@Component("calculator")
//@Scope("prototype")
public class CalculatorImpl implements Calculator {
    private static final Logger logger = LogManager.getLogger(CalculatorImpl.class);
    private GreetingService gr;

    @Override
    public int add(int a, int b) {
        return a + b;
    }

    @Override
    public int mul(int x, int y) {
        return x * y;
    }

    @PostConstruct
    private void postConstruct() {
        logger.info("postConstruct");
    }

    @PreDestroy
    private void preDestroy() {
        logger.info("preDestroy");
    }

    @Required
    @Autowired
    public void setGreetingService(GreetingService gr) {
        this.gr = gr;
    }
}
