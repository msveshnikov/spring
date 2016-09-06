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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    @Autowired(required = false)
    OrderRepository orders;
    @Autowired(required = false)
    private CustomerRepository repository;

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

    public void run(String... args) throws Exception {

        //repository.deleteAll();

        // save a couple of customers
        repository.save(new Customer("Alice", "Smith"));
        repository.save(new Customer("Bob", "Smith"));

        // fetch all customers
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (Customer customer : repository.findAll()) {
            System.out.println(customer);
        }
        System.out.println();

        // fetch an individual customer
        System.out.println("Customer found with findByFirstName('Alice'):");
        System.out.println("--------------------------------");
        Customer alice = repository.findByFirstName("Alice");
        System.out.println(alice);

        System.out.println("Customers found with findByLastName('Smith'):");
        System.out.println("--------------------------------");

        for (Customer customer : repository.findByLastName("Smith")) {
            System.out.println(customer);
        }

        System.out.println(repository.count());

        System.out.println("Creating order:");
        orders.save(new Order(alice, "23/47"));
        System.out.println("Order list:");
        for (Order order : orders.findAll()) {
            System.out.println(order);
        }
    }
}