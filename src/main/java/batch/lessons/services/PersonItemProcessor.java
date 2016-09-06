package batch.lessons.services;

import batch.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import javax.swing.text.MaskFormatter;

public class PersonItemProcessor implements ItemProcessor<Person, Person> {

    private static final Logger log = LoggerFactory.getLogger(PersonItemProcessor.class);

    @Override
    public Person process(final Person person) throws Exception {
        final String name = person.getFirstName().toUpperCase();
        final String title = person.getTitle().toUpperCase();

        log.info("Converting phone:" + person.getPhone());
        String phoneMask = "(###) ###-####";
        String phoneNumber = person.getPhone().replace("(", "").replace(")", "").replace("-", "").replace(" ", "").replace(".", "");
        MaskFormatter maskFormatter = new MaskFormatter(phoneMask);
        maskFormatter.setValueContainsLiteralCharacters(false);
        String phone;
        log.info("Cleaned phone:" + phoneNumber);
        try {
            phone = maskFormatter.valueToString(phoneNumber);
        } catch (Exception e) {
            phone = person.getPhone();
        }

        final Person transformedPerson = new Person(name, title, phone, person.getState(), person.getCounty());

        log.info("Converting (" + person + ") into (" + transformedPerson + ")");

        return transformedPerson;
    }

}