package batch.lessons;

import batch.lessons.services.TestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

/**
 * Конфигурационный класс Spring IoC контейнера
 */
@Configuration
@ComponentScan
@Import(AnotherConfiguration.class)
@PropertySource("classpath:app.properties")
public class LessonsConfiguration {

    @Autowired
    Environment env;

    @Bean
    public TestBean testBean() {
        TestBean testBean = new TestBean();
        testBean.setName(env.getProperty("testbean.name"));
        return testBean;
    }

}
