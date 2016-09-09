package batch.UI;

/**
 * Created by Max Sveshnikov on 08.09.16.
 */

import batch.model.Person;
import batch.repos.PersonRepository;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

@SpringUI
@Theme("valo")
public class VaadinUI extends UI {


    private final PersonRepository repo;
    private final PersonEditor editor;

    private Grid grid;
    private final TextField filter;
    private final Button addNewBtn;

    @Autowired
    public VaadinUI(PersonRepository repo, PersonEditor editor) {
        this.repo = repo;
        this.editor = editor;
        this.grid = new Grid();
        this.filter = new TextField();
        this.addNewBtn = new Button("New person", FontAwesome.PLUS);
    }

    @Override
    protected void init(VaadinRequest request) {
        // build layout
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        VerticalLayout mainLayout = new VerticalLayout(actions, grid, editor);
        setContent(mainLayout);

        // Configure layouts and components
        actions.setSpacing(true);
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        grid.setHeight(300, Unit.PIXELS);
        grid.setWidth(800, Unit.PIXELS);
        grid.setColumns("id", "firstName", "title", "state", "phone", "sex");

        filter.setInputPrompt("Filter by last name");

        // Hook logic to components

        // Replace listing with filtered content when user changes filter
        filter.addTextChangeListener(e -> listPersons(e.getText()));

        // Connect selected Person to editor or hide if none is selected
        grid.addSelectionListener(e -> {
            if (e.getSelected().isEmpty()) {
                editor.setVisible(false);
            } else {
                editor.editPerson((Person) grid.getSelectedRow());
            }
        });

        // Instantiate and edit new Person the new button is clicked
        addNewBtn.addClickListener(e -> editor.editPerson(new Person("", "", "", "", "")));

        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listPersons(filter.getValue());
        });

        // Initialize listing
        listPersons(null);
    }

    private void listPersons(String text) {
        if (StringUtils.isEmpty(text)) {
            grid.setContainerDataSource(
                    new BeanItemContainer(Person.class, repo.findAll()));
        } else {
            grid.setContainerDataSource(new BeanItemContainer(Person.class,
                    repo.findByTitleStartsWithIgnoreCase(text)));
        }
    }

}