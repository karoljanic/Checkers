package org.checkers.games;

import org.checkers.boards.Board;
import org.checkers.database.DatabaseManager;
import org.checkers.database.entities.MoveEntity;
import org.checkers.database.entities.TurnEntity;
import org.checkers.enums.CheckerColor;
import org.checkers.enums.GameType;
import org.checkers.utils.CustomClock;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ReplayCheckersGame extends CheckerGame {
    private Socket player;

    public ReplayCheckersGame(Socket player, int gameId, GameType gameType, Board board, DatabaseManager databaseManager) {
        super(gameType, board, databaseManager);

        this.player = player;
        this.gameIdInDatabase = gameId;
    }
    @Override
    public void run() {
        try {
            PrintWriter out = new PrintWriter(player.getOutputStream(), true);

            out.println("set-id/0");
            out.println("init-board" + prepareBoardDescription());
            out.println("move-now/1");

            CustomClock.waitMillis(3000);

            ArrayList<TurnEntity> turns = databaseManager.getAllTurns(gameIdInDatabase);
            for(TurnEntity turn: turns) {
                ArrayList<MoveEntity> moves = databaseManager.getAllMoves(turn.getId());
                for(MoveEntity move: moves) {
                    int x1 = move.getStartX();
                    int y1 = move.getStartY();
                    int x2 = move.getEndX();
                    int y2 = move.getEndY();

                    board.move(x1, y1, x2, y2, turn.getCheckerColor().equals("white") ? CheckerColor.WHITE : CheckerColor.BLACK);

                    out.println("update-piece-position/" + x1 + "/" + y1 + "/" + x2 + "/" + y2);

                    int dx = x2 > x1 ? 1 : -1;
                    int dy = y2 > y1 ? 1 : -1;
                    int steps = Math.abs(x2 - x1);

                    CustomClock.waitMillis(200);

                    if (move.getMoveType().equals("beat")) {
                        for (int x = x1, y = y1, iter = 0; iter < steps; iter++, x += dx, y += dy) {
                            System.out.println("x = " + x + "  y = " + y);
                            if (board.coordinateIsWithPiece(x, y, turn.getCheckerColor().equals("white") ? CheckerColor.BLACK : CheckerColor.WHITE)) {
                                board.removePiece(x, y);
                                out.println("remove-piece/" + x + "/" + y);
                            }
                        }
                    }

                    if (board.isKing(x2, y2)) {
                        out.println("update-piece-to-king/" + x2 + "/" + y2);
                    }

                    CustomClock.waitMillis(1000);
                }
            }

            CustomClock.waitMillis(1500);
            out.println("end/");

        }
        catch(Exception ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
