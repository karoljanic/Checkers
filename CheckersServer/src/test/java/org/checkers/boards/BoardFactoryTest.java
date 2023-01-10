package org.checkers.boards;

import org.checkers.enums.GameType;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardFactoryTest {
    @Test
    public void testBoardFactory() {
        BoardFactory boardFactory1 = BoardFactory.getFactory();
        BoardFactory boardFactory2 = BoardFactory.getFactory();
        BoardFactory boardFactory3 = BoardFactory.getFactory();

        Board board1 = boardFactory1.getBoard(GameType.BRAZILIAN);
        assertInstanceOf(BrazilianBoard.class, board1);

        Board board2 = boardFactory2.getBoard(GameType.THAI);
        assertInstanceOf(ThaiBoard.class, board2);

        Board board3 = boardFactory3.getBoard(GameType.INTERNATIONAL);
        assertInstanceOf(InternationalBoard.class, board3);
    }
}
