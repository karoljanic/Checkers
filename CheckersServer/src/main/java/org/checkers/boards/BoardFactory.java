package org.checkers.boards;

import org.checkers.enums.GameType;

/**
 * klasa tworzy odpowiednią instancję klasy dziedziczącej po Board
 */
public class BoardFactory {
    /**
     * nowo utworzona instancja klasy dziedziczącej po Board
     */
    private static BoardFactory boardFactoryInstance;

    /**
     * @return nowa instancja klasy BoardFactory
     */
    public static BoardFactory getFactory() {
        if(boardFactoryInstance == null) {
            boardFactoryInstance = new BoardFactory();
        }

        return boardFactoryInstance;
    }

    /**
     * @param gameType typ gry
     * @return nowa instancja klasy Board odpowiedniego typu
     */
    public Board getBoard(GameType gameType) {
        return switch (gameType) {
            case INTERNATIONAL -> new InternationalBoard();
            case BRAZILIAN -> new BrazilianBoard();
            case THAI -> new ThaiBoard();
        };
    }

    private BoardFactory() { }
}
