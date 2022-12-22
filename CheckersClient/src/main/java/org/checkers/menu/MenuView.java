package org.checkers.menu;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.checkers.utils.WindowProperties;

import java.util.ArrayList;

public class MenuView extends VBox {
    private final ArrayList<Button> buttons = new ArrayList<>();

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
    }

    public ArrayList<Button> getButtons() {
        return buttons;
    }
}
