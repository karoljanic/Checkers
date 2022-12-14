package org.checkers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.checkers.menu.MenuController;

public class Game extends Application implements Runnable {

    @Override
    public void init() { }

    @Override
    public void start(Stage primaryStage) {
        final Scene scene = new Scene(new MenuController(primaryStage).getView());
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    @Override
    public void stop() { }

    @Override
    public void run() {

    }
    public static void main(String[] args) { launch(args); }
}