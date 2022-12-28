package org.checkers.boards;

import org.checkers.enums.GameType;

public class BoardFactory {
    private static BoardFactory boardFactoryInstance;

    public static BoardFactory getFactory() {
        if(boardFactoryInstance == null) {
            boardFactoryInstance = new BoardFactory();
        }

        return boardFactoryInstance;
    }

    public Board getBoard(GameType gameType) {
        return switch (gameType) {
            case INTERNATIONAL -> new InternationalBoard();
            case BRAZILIAN -> new BrazilianBoard();
            case THAI -> new ThaiBoard();
        };
    }

    private BoardFactory() { }
}
