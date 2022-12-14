package org.checkers.menu;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.checkers.board.BoardController;
import org.checkers.server.ServerService;
import org.checkers.utils.GameType;

import java.util.ArrayList;

public class MenuController implements EventHandler {
    private final Menu model;
    private final MenuView view;

    private final Stage stage;

    public MenuController(Stage primaryStage) {
        ArrayList<String> gameTypes = new ArrayList<>();
        for(GameType gameType: GameType.values()) {
            gameTypes.add(gameType.toString());
        }
        stage = primaryStage;
        model = new Menu(gameTypes);
        view = new MenuView(model.getCheckersTypes(), this);
    }

    public void setCheckersTypes(ArrayList<String> types) {
        model.setCheckersTypes(types);
    }

    public ArrayList<String> getCheckersTypes() {
        return model.getCheckersTypes();
    }

    public MenuView getView() {
        return view;
    }

    @Override
    public void handle(Event event) {
        Object source = event.getSource();
        for(Button button: view.getButtons()) {
            if(source.equals(button)) {
                //stage.hide();
                GameType gameType = GameType.valueOf(button.getText());
                ServerService.initializeCheckers(gameType);
            }
        }
    }
}


//final ControllerB controllerB = new ControllerB(primaryStage);
//final Scene scene = new Scene(controllerB.getView());
 //       primaryStage.setScene(scene);
