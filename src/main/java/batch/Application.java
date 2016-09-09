package batch;

import batch.lessons.LessonsConfiguration;
import batch.lessons.services.BeanWithDependency;
import batch.lessons.services.interfaces.GreetingService;
import batch.model.Customer;
import batch.model.Order;
import batch.model.Person;
import batch.repos.CustomerRepository;
import batch.repos.OrderRepository;
import batch.repos.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.Date;

import static java.lang.Thread.sleep;

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
    private PersonRepository personRepository;
    @Autowired
    private Job importUserJob;


    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        //printBeans(ctx);
    }

    private static void printBeans(ApplicationContext ctx) {
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        System.out.println("Let's inspect the beans provided by Spring Boot:");
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }

//    @Bean
//    CommandLineRunner init(AccountRepository accountRepository,
//                           BookmarkRepository bookmarkRepository) {
//        return evt -> Arrays.asList(
//                "jhoeller,dsyer,pwebb,ogierke,rwinch,mfisher,mpollack,jlong".split(","))
//                .forEach(
//                        a -> {
//                            Account account = accountRepository.save(new Account(a,
//                                    "password"));
//                            bookmarkRepository.save(new Bookmark(account,
//                                    "http://bookmark.com/1/" + a, "A description"));
//                            bookmarkRepository.save(new Bookmark(account,
//                                    "http://bookmark.com/2/" + a, "A description"));
//                        });
//    }

    private void testBeans() {
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

    //@Scheduled(fixedRate = 5000)
    public void reportCurrentTime() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, InterruptedException {
        logger.info("The time is now {}", new Date());
        sleep(5000); // conflict with default job run - sqlite is locked
        System.out.println("Starting job");
        jobLauncher.run(importUserJob, new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis()).toJobParameters()); //need new JobParams to start each step again
    }

    @Override
    public void run(String... args) throws Exception {
        testBeans();
        testMongo();
        testSQL();
    }

    private void testSQL() {
        //personRepository.deleteAll();
        personRepository.save(new Person("Me", "programmer", "333", "", ""));
        personRepository.save(new Person("Other", "QA", "333", "", ""));
        logger.info("Persons found with findAll():");
        logger.info("-------------------------------");
        for (Person person : personRepository.findAll()) {
            logger.info(person.toString());
        }
    }

    private void testMongo() {
        customerRepository.deleteAll();

        // save a couple of customers
        customerRepository.save(new Customer("Alice", "Smith"));
        customerRepository.save(new Customer("Bob", "Smith"));

        // fetch all customers
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        customerRepository.findAll().forEach(System.out::println);
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