package org.checkers.menu;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.checkers.utils.WindowProperties;

import java.util.ArrayList;

/**
 * klasa odpowiada za wyświetlanie początkowego menu użytkownikowi
 */
public class MenuView extends VBox {
    /**
     * przyciski do wyboru rodzaju gry
     */
    private final ArrayList<Button> buttons = new ArrayList<>();
    /**
     * checkbox daje wybór użytkownikowi, czy chce grać z botem
     */
    private final CheckBox againstBotCheckBox;

    /**
     * @param gameTypes możliwe rodzaje gry
     * @param menuController obiekt do obsługi działania menu
     * konstruktor ustawia niezbędne parametry nowego obiektu i ustawia jego wygląd
     */
    MenuView(ArrayList<String> gameTypes, MenuController menuController) {
        double buttonHeight = WindowProperties.calculateWindowStageSize() / 9.0;
        double buttonWidth = WindowProperties.calculateWindowStageSize() / 3.0;

        setAlignment(Pos.CENTER);

        Label title = new Label("Choose checkers type:");
        title.setPrefHeight(buttonHeight / 1.5);
        getChildren().add(title);

        for (String type: gameTypes) {
            Button button = new Button(type);

            button.setPrefHeight(buttonHeight);
            button.setPrefWidth(buttonWidth);
            button.setOnAction(menuController);

            getChildren().add(button);
            buttons.add(button);
        }

        againstBotCheckBox = new CheckBox("I want to play against bot");
        againstBotCheckBox.setPrefHeight(buttonWidth / 6.0);
        getChildren().add(againstBotCheckBox);
    }

    /**
     * @return przyciski do wyboru rodzaju gry
     */
    public ArrayList<Button> getButtons() {
        return buttons;
    }

    public boolean playAgainstBot() {
        return againstBotCheckBox.isSelected();
    }
}
