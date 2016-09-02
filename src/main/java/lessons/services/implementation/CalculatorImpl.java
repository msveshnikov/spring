package lessons.services.implementation;

import lessons.services.interfaces.Calculator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by max on 02.09.16.
 */

@Component("calculator")
@Scope("prototype")
public class CalculatorImpl implements Calculator {
    private static final Logger logger = LogManager.getLogger(CalculatorImpl.class);

    @Override
    public int add(int a, int b) {
        return a + b;
    }

    @PostConstruct
    private void postConstruct() {
        logger.info("postConstruct");
    }

    @PreDestroy
    private void preDestroy() {
        logger.info("preDestroy");
    }

}
