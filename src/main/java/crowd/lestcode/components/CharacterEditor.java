package crowd.lestcode.components;

//https://spring.io/guides/gs/crud-with-vaadin/

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import crowd.lestcode.domain.Character;
import crowd.lestcode.repo.CharacterRepo;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class CharacterEditor extends VerticalLayout implements KeyNotifier {

    private final CharacterRepo characterRepo;

    private Character character;

    TextField firstName  = new TextField("First name");
    TextField lastName   = new TextField("Last name");
    TextField patronymic = new TextField("Patronymic");

    private Button save   = new Button("Save", VaadinIcon.CHECK.create());
    private Button cancel = new Button("Cancel");
    private Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    private HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    private Binder<Character> binder = new Binder<>(Character.class);
    @Setter
    private ChangeHandler сhangeHandler;

    public interface ChangeHandler{
        void onChange();
    }

    @Autowired
    public CharacterEditor(CharacterRepo characterRepo) {

        this.characterRepo = characterRepo;
        add( firstName,lastName, patronymic, actions);

        binder.bindInstanceFields(this);


        setSpacing(true);
        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

         save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editCharacter(character));
        setVisible(false);

    }
    private void delete() {
        characterRepo.delete(character);
        сhangeHandler.onChange();
    }

    private void save() {
        characterRepo.save(character);
        сhangeHandler.onChange();
    }

    public void editCharacter(Character character){
        if (character == null){
            setVisible(false);
            return;
        }
        if (character.getId() != null){
            this.character = characterRepo.findById(character.getId()).orElse(character);
        } else {
            this.character = character;
        }
        binder.setBean(character);
        setVisible(true);
        lastName.focus();
    }
}
