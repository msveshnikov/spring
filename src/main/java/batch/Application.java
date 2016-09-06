package batch;

import batch.lessons.LessonsConfiguration;
import batch.lessons.services.BeanWithDependency;
import batch.lessons.services.interfaces.GreetingService;
import batch.model.Customer;
import batch.model.CustomerRepository;
import batch.model.Order;
import batch.model.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@SpringBootApplication
public class Application implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    @Autowired
    JobLauncher jobLauncher;
    @Autowired
    private OrderRepository orders;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private Job importUserJob;

    public static void main(String[] args) throws Exception {

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

        SpringApplication.run(Application.class, args);

    }

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        logger.info("The time is now {}", new Date());
        System.out.println("Starting job");
        jobLauncher.run(importUserJob, new JobParameters());
    }

    @Override
    public void run(String... args) throws Exception {

        customerRepository.deleteAll();

        // save a couple of customers
        customerRepository.save(new Customer("Alice", "Smith"));
        customerRepository.save(new Customer("Bob", "Smith"));

        // fetch all customers
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (Customer customer : customerRepository.findAll()) {
            System.out.println(customer);
        }
        System.out.println();

        // fetch an individual customer
        System.out.println("Customer found with findByFirstName('Alice'):");
        System.out.println("--------------------------------");
        Customer alice = customerRepository.findByFirstName("Alice");
        System.out.println(alice);

        System.out.println("Customers found with findByLastName('Smith'):");
        System.out.println("--------------------------------");

        for (Customer customer : customerRepository.findByLastName("Smith")) {
            System.out.println(customer);
        }

        System.out.println(customerRepository.count());

        System.out.println("Creating order:");
        orders.save(new Order(alice, "23/47"));
        System.out.println("Order list:");
        for (Order order : orders.findAll()) {
            System.out.println(order);
        }
    }
}