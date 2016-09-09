package batch.UI;

/**
 * Created by Max Sveshnikov on 09.09.16.
 */

import batch.model.Person;
import batch.repos.PersonRepository;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A simple example to introduce building forms. As your real application is
 * probably much more complicated than this example, you could re-use this form in
 * multiple places. This example component is only used in VaadinUI.
 * <p>
 * In a real world application you'll most likely using a common super class for all your
 * forms - less code, better UX. See e.g. AbstractForm in Virin
 * (https://vaadin.com/addon/viritin).
 */
@SpringComponent
@UIScope
public class PersonEditor extends VerticalLayout {

    private final PersonRepository repository;

    /**
     * The currently edited Person
     */
    private Person Person;

    /* Fields to edit properties in Person entity */
    TextField firstName = new TextField("First name");
    TextField title = new TextField("Title");
    TextField phone = new TextField("Phone");
    TextField state = new TextField("State");
    TextField county = new TextField("County");

    /* Action buttons */
    Button save = new Button("Save", FontAwesome.SAVE);
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", FontAwesome.TRASH_O);
    CssLayout actions = new CssLayout(save, cancel, delete);

    @Autowired
    public PersonEditor(PersonRepository repository) {
        this.repository = repository;

        addComponents(firstName, title, phone, state, county, actions);

        // Configure and style components
        setSpacing(true);
        actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> repository.save(Person));
        delete.addClickListener(e -> repository.delete(Person));
        cancel.addClickListener(e -> editPerson(Person));
        setVisible(false);
    }

    public interface ChangeHandler {

        void onChange();
    }

    public final void editPerson(Person c) {
        final boolean persisted = c.getId() != null;
        if (persisted) {
            // Find fresh entity for editing
            Person = repository.findOne(c.getId());
        } else {
            Person = c;
        }
        cancel.setVisible(persisted);

        // Bind Person properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        BeanFieldGroup.bindFieldsUnbuffered(Person, this);

        setVisible(true);

        // A hack to ensure the whole form is visible
        save.focus();
        // Select all text in firstName field automatically
        firstName.selectAll();
    }

    public void setChangeHandler(ChangeHandler h) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        save.addClickListener(e -> h.onChange());
        delete.addClickListener(e -> h.onChange());
    }

}