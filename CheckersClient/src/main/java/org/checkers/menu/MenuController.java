package org.checkers.menu;

import javafx.stage.Stage;

public class MenuController {
    private final Menu model;
    private final MenuView view;

    public MenuController(Menu menuModel, MenuView menuView) {
        model = menuModel;
        view = menuView;
    }

    public void setCheckersTypes(String[] types) {
        model.setCheckersTypes(types);
    }

    public String[] getCheckersTypes() {
        return model.getCheckersTypes();
    }

    public void updateView() {
        view.update(model.getCheckersTypes());
    }
}
