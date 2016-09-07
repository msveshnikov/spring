package batch;

/**
 * Created by Max Sveshnikov on 05.09.16.
 */

import batch.lessons.services.JobCompletionNotificationListener;
import batch.lessons.services.PersonItemProcessor;
import batch.model.Customer;
import batch.model.CustomerRepository;
import batch.model.Person;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;


@Configuration
@EnableBatchProcessing
@ComponentScan
@PropertySource("classpath:app.properties")
@EnableScheduling
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Resource // not an @AutoWired because of order of wiring
    public Environment env;

    @Autowired
    public DataSource dataSource; // set from app.properties

    @Autowired
    private CustomerRepository customerRepository;


    @Bean
    public FlatFileItemReader<Person> reader() {
        FlatFileItemReader<Person> reader = new FlatFileItemReader<Person>();
        reader.setMaxItemCount(20);
        reader.setResource(new ClassPathResource("contacts.csv"));
        reader.setLineMapper(new DefaultLineMapper<Person>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"1", "state", "county", "4", "title", "firstName", "phone"
                        , "f1", "f2", "f3", "f4", "f5", "f6", "f7"
                });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
                setStrict(false); // as column quantity misequal
                setDistanceLimit(1); // to fix bug with NonWriteable property
                setTargetType(Person.class);
            }});
        }});
        return reader;
    }

    @Bean
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Person> mysqlWriter() {
        JdbcBatchItemWriter<Person> writer = new JdbcBatchItemWriter<Person>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Person>());
        writer.setSql("INSERT INTO people (title, firstname, phone, state, county) VALUES (:title, :firstName, :phone, :state, :county);");
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public ItemWriter<Person> mongoWriter() {
        return new ItemWriter<Person>() {
            @Override
            public void write(List<? extends Person> list) throws Exception {
                for (Person p : list) {
                    customerRepository.save(new Customer(p.getFirstName(), p.getTitle()));
                }
            }
        };
    }

    @Bean
    public JobExecutionListener listener() {
        return new JobCompletionNotificationListener(new JdbcTemplate(dataSource));
    }

    @Bean
    public Job importUserJob() {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener())
                .flow(step1())
                .end()
                .build();
    }


    private CompositeItemWriter compositeItemWriter() {
        CompositeItemWriter writer = new CompositeItemWriter();
        writer.setDelegates(Arrays.asList(mysqlWriter(), mongoWriter()));
        return writer;
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Person, Person>chunk(500)
                .reader(reader())
                .processor(processor())
                .writer(compositeItemWriter())
                .build();
    }

}