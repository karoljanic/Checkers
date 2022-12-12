package org.checkers.menu;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.checkers.utils.WindowProperties;

public class MenuView {
    void update(String[] gameTypes) {
        double windowSize = WindowProperties.calculateWindowStageSize() / 2.0;

        VBox menuContainer = new VBox();
        menuContainer.setPrefHeight(windowSize);
        menuContainer.setPrefWidth(windowSize);

        for (String type: gameTypes) {
            menuContainer.getChildren().add(new Button(type));
        }

        Stage stage = new Stage();
        stage.setScene(new Scene(menuContainer));
        stage.setTitle("Checkers Menu");
        stage.setResizable(true);

        stage.show();
        stage.setMaxHeight(stage.getHeight());
        stage.setMaxWidth(stage.getWidth());
    }
}
