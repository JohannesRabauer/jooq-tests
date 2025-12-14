package dev.rabauer.jooq.example;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import dev.rabauer.jooq.example.db.DbService;
import dev.rabauer.jooq.example.db.SchoolClass;
import dev.rabauer.jooq.example.db.SchoolClassRepository;
import jakarta.inject.Inject;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

/**
 * The main view contains a button and a click listener.
 */
@Route("")
public class MainView extends VerticalLayout {

    @Inject
    GreetService greetService;

    @Inject
    SchoolClassRepository schoolClassRepository;

    @Inject
    DbService dbService;

    public MainView() {
        // Use TextField for standard text input
        TextField textField = new TextField("Your name");
        textField.addThemeName("bordered");

        HorizontalLayout hlContainer = new HorizontalLayout();
        hlContainer.setSizeFull();
        // Button click listeners can be defined as lambda expressions
        Button button = new Button("CreateGrid", e -> {
            hlContainer.removeAll();
            hlContainer.add(createGrid(Long.parseLong(textField.getValue())));
        });

        // Theme variants give you predefined extra styles for components.
        // Example: Primary button is more prominent look.
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // You can specify keyboard shortcuts for buttons.
        // Example: Pressing enter in this view clicks the Button.
        button.addClickShortcut(Key.ENTER);

        // Use custom CSS classes to apply styling. This is defined in
        // shared-styles.css.
        addClassName("centered-content");

        Button button2 = new Button("AddStuff", e -> {
            dbService.create();
        });

        add(textField, button, button2, hlContainer);
    }

    private Grid<SchoolClass> createGrid(long schoolId) {
        Grid<SchoolClass> grid = new Grid<>(SchoolClass.class, false);
        grid.setSizeFull();

        grid.addColumn(SchoolClass::id)
                .setHeader("ID")
                .setAutoWidth(true);

        grid.addColumn(SchoolClass::name)
                .setHeader("Class name")
                .setFlexGrow(1);

        grid.addColumn(SchoolClass::schoolId)
                .setHeader("School ID")
                .setAutoWidth(true);

        grid.addColumn(SchoolClass::teacherId)
                .setHeader("Teacher ID")
                .setAutoWidth(true);

        grid.setItems(schoolClassRepository.findBySchool(schoolId));

        return grid;
    }
}
