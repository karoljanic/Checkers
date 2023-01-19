package org.checkers.menu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.checkers.server.ServerService;
import org.checkers.utils.GameType;

import java.util.ArrayList;

/**
 * klasa obsługuje klasę Menu i MenuView
 */
public class MenuController implements EventHandler<ActionEvent> {
    /**
     * instancja klasy Menu
     */
    private final Menu model;
    /**
     * instancja klasy MenuView
     */
    private MenuView view;
    /**
     * instancja klasy Stage do wyświetlania menu
     */
    private final Stage stage;

    /**
     * @param stage instancja klasy Stage do wyświetlania menu
     * konstruktor ustawia niezbędne parametry nowego obiektu
     */
    public MenuController(Stage stage) {
        ArrayList<String> gameTypes = new ArrayList<>();
        for(GameType gameType: GameType.values()) {
            gameTypes.add(gameType.toString());
        }

        this.stage = stage;
        model = new Menu(gameTypes);
        view = new MenuView(model.getCheckersTypes(), this);
    }

    /**
     * funkcja wyświetla menu dla użytkownika
     */
    public void showView() {
        view = new MenuView(model.getCheckersTypes(), this);

        final Scene scene = new Scene(view);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    /**
     * funkcja zamyka okno gry
     */
    public void closeStage() {
        stage.close();
    }

    /* (non-Javadoc)
     * @see javafx.event.EventHandler#handle(javafx.event.Event)
     */
    @Override
    public void handle(ActionEvent event) {
        Object source = event.getSource();
        for(Button button: view.getButtons()) {
            if(source.equals(button)) {
                GameType gameType = GameType.valueOf(button.getText());
                ServerService.initializeCheckers(gameType, view.playAgainstBot());
            }
        }
    }
}

