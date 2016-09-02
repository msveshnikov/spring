package lessons.services;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Created by max on 02.09.16.
 */
public class GreetingServiceImpl implements GreetingService {
    private static final Logger logger = LogManager.getLogger(GreetingServiceImpl.class);

    public GreetingServiceImpl() {
        logger.info("Creating GreetingServiceImpl");
    }

    @Override
    public String sayGreeting() {
        return "Greeting, user!";
    }
}