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
        //setContent(new Button("Click me", e -> Notification.show("Hello Spring+Vaadin user!")));
        //setContent(grid);
        listCustomers();

        Panel panel = new Panel("My Custom Component");
        VerticalLayout panelContent = new VerticalLayout();
        panelContent.setMargin(true); // Very useful
        panel.setContent(panelContent);

        // Compose from multiple components
        panelContent.addComponent(new Button("Click me", e -> Notification.show("Hello Spring+Vaadin user!")));
        panelContent.addComponent(grid);

        // Set the size as undefined at all levels
        panelContent.setSizeUndefined();
        panel.setSizeUndefined();
        setSizeUndefined();

        setContent(panel);
    }

    private void listCustomers() {
        grid.setContainerDataSource(
                new BeanItemContainer(Person.class, repo.findAll()));
    }

}