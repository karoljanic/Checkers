package org.checkers.menu;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

    private final Button showSavedGamesButton;

    private final Button replayGameButton;
    private final CheckBox againstBotCheckBox;

    private final TextField gameToReplayId;

    /**
     * @param gameTypes możliwe rodzaje gry
     * @param menuController obiekt do obsługi działania menu
     * konstruktor ustawia niezbędne parametry nowego obiektu i ustawia jego wygląd
     */
    MenuView(ArrayList<String> gameTypes, MenuController menuController) {
        double buttonHeight = WindowProperties.calculateWindowStageSize() / 12.0;
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
        againstBotCheckBox.setPrefHeight(buttonWidth / 5.0);
        getChildren().add(againstBotCheckBox);

        showSavedGamesButton = new Button("Show saved games");

        showSavedGamesButton.setPrefHeight(buttonHeight);
        showSavedGamesButton.setPrefWidth(buttonWidth);
        showSavedGamesButton.setOnAction(menuController);

        getChildren().add(showSavedGamesButton);

        gameToReplayId  = new TextField();
        gameToReplayId.setPrefHeight(buttonHeight / 2.0);
        gameToReplayId.setPrefWidth(buttonWidth);
        getChildren().add(gameToReplayId);

        replayGameButton = new Button("Replay game with given ID");

        replayGameButton.setPrefHeight(buttonHeight);
        replayGameButton.setPrefWidth(buttonWidth);
        replayGameButton.setOnAction(menuController);

        getChildren().add(replayGameButton);
    }

    /**
     * @return przyciski do wyboru rodzaju gry
     */
    public ArrayList<Button> getButtons() {
        return buttons;
    }

    public Button getShowSavedGamesButton() { return showSavedGamesButton; }

    public Button getReplayGameButton() { return replayGameButton; }

    public boolean playAgainstBot() {
        return againstBotCheckBox.isSelected();
    }

    public int getGameToReplayId() { return Integer.parseInt(gameToReplayId.getText()); }

}
