package batch.UI;

/**
 * Created by Max Sveshnikov on 08.09.16.
 */

import batch.model.Person;
import batch.repos.PersonRepository;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

@SpringUI
@Theme("valo")
public class VaadinUI extends UI {


    PersonRepository repo;
    Grid grid;

    @Autowired
    public VaadinUI(PersonRepository repo) {
        this.repo = repo;
        this.grid = new Grid();
    }

    @Override
    protected void init(VaadinRequest request) {
        TextField filter = new TextField();
        filter.setInputPrompt("Filter by job title");
        filter.addTextChangeListener(e -> listCustomers(e.getText()));
        VerticalLayout mainLayout = new VerticalLayout(new Button("Click me", e -> Notification.show("Hello Spring+Vaadin user!")), filter, grid);
        setContent(mainLayout);
    }

    void listCustomers(String text) {
        if (StringUtils.isEmpty(text)) {
            grid.setContainerDataSource(
                    new BeanItemContainer(Person.class, repo.findAll()));
        } else {
            grid.setContainerDataSource(new BeanItemContainer(Person.class,
                    repo.findByTitleStartsWithIgnoreCase(text)));
        }
    }

}