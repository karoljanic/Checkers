package org.checkers.menu;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.checkers.board.BoardController;
import org.checkers.server.ServerService;
import org.checkers.utils.GameType;

import java.util.ArrayList;

public class MenuController implements EventHandler<ActionEvent> {
    private final Menu model;
    private MenuView view;
    private final Stage stage;

    public MenuController(Stage stage) {
        ArrayList<String> gameTypes = new ArrayList<>();
        for(GameType gameType: GameType.values()) {
            gameTypes.add(gameType.toString());
        }

        //stage = new Stage();
        this.stage = stage;
        model = new Menu(gameTypes);
        view = new MenuView(model.getCheckersTypes(), this);
    }

    public void showView() {
        view = new MenuView(model.getCheckersTypes(), this);

        final Scene scene = new Scene(view);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }


    @Override
    public void handle(ActionEvent event) {
        Object source = event.getSource();
        for(Button button: view.getButtons()) {
            if(source.equals(button)) {
                GameType gameType = GameType.valueOf(button.getText());
                ServerService.initializeCheckers(gameType);
            }
        }
    }
}

