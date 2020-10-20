
package crowd.lestcode.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import crowd.lestcode.components.CharacterEditor;
import crowd.lestcode.domain.Character;
import crowd.lestcode.repo.CharacterRepo;
import org.springframework.beans.factory.annotation.Autowired;

@Route
public class MainView extends VerticalLayout {

    private final CharacterRepo characterRepo;
    private Grid<Character> grid = new Grid<>(Character.class);
    private final TextField filter = new TextField("", "Type to filter");
    private final Button addNewBtn = new Button("Add new");
    private final HorizontalLayout toolbar = new HorizontalLayout(filter, addNewBtn);
    private final CharacterEditor editor;

    @Autowired
    public MainView(CharacterRepo characterRepo, CharacterEditor editor) {

        this.characterRepo = characterRepo;
        this.editor = editor;
        add(grid);
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> showCharacter(e.getValue()));

        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editCharacter(e.getValue());
        });

        addNewBtn.addClickListener(e -> editor.editCharacter(new Character()));

        editor.setСhangeHandler(() -> {
            editor.setVisible(false);
            showCharacter(filter.getValue());
        });

        showCharacter("");
    }

    private void showCharacter(String name) {

        if (name.isEmpty()) {
            grid.setItems(characterRepo.findAll());
        } else {

            grid.setItems(characterRepo.findByName(name));
        }

    }
}

