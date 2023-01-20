package org.checkers.games;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

import org.checkers.boards.Board;
import org.checkers.database.DatabaseManager;
import org.checkers.database.IDatabase;
import org.checkers.enums.GameType;
import org.checkers.piece.coordinate.Coordinate;
import org.checkers.piece.coordinate.CoordinatesArray;
import org.checkers.piece.coordinate.PathsArray;
import org.checkers.enums.CheckerColor;
import org.checkers.utils.CustomClock;
import org.checkers.enums.GameStatus;

/**
 * klasa obsługuje grę między dwoma klientami
 */
public class NoBotCheckersGame extends CheckerGame {
    /**
     * połączenie z pierwszym klientem
     */
    private final Socket player1;
    /**
     * połączenie z drugim klientem
     */
    private final Socket player2;

    /**
     * @param player1 połączenie z pierwszym klientem
     * @param player2 połączenie z drugim klientem
     * @param board plansza do gry
     * konstruktor ustawia niezbędne paramatery dla nowego obiektu
     */
    public NoBotCheckersGame(Socket player1, Socket player2, GameType gameType, Board board, DatabaseManager databaseManager) {
        super(gameType, board, databaseManager);

        boolean changeOrder = new Random(System.currentTimeMillis()).nextBoolean();
        if(changeOrder) {
            this.player1 = player2;
            this.player2 = player1;
        }
        else {
            this.player1 = player1;
            this.player2 = player2;
        }

        gameStatus = GameStatus.WHITE_TURN;

        gameIdInDatabase = databaseManager.addNewGame(gameType);
        currentTurn = 0;
    }

    /**
     * funkcja komunikuje się z klientami nadzorując przebieg gry
     */
    @Override
    public void run() {
        try {
            board.generatePossibleMoves();

            PrintWriter out1 = new PrintWriter(player1.getOutputStream(), true);
            BufferedReader in1 = new BufferedReader(new InputStreamReader(player1.getInputStream()));

            PrintWriter out2 = new PrintWriter(player2.getOutputStream(), true);
            BufferedReader in2 = new BufferedReader(new InputStreamReader(player2.getInputStream()));

            out1.println("set-id/" + CheckerColor.WHITE.ordinal());
            out2.println("set-id/" + CheckerColor.BLACK.ordinal());

            out1.println("init-board" + prepareBoardDescription());
            out2.println("init-board" + prepareBoardDescription());

            out1.println("possible-moves" + preparePossibleMoves(CheckerColor.WHITE));
            out2.println("possible-moves" + preparePossibleMoves(CheckerColor.BLACK));

            PrintWriter out, otherOut;
            BufferedReader in;
            int playerIdWithMove;
            CheckerColor color, otherColor;

            while(gameStatus != GameStatus.FINISHED) {
                if (gameStatus == GameStatus.WHITE_TURN) {
                    color = CheckerColor.WHITE;
                    otherColor = CheckerColor.BLACK;
                    out = out1;
                    otherOut = out2;
                    in = in1;
                    playerIdWithMove = 0;
                }
                else { //turn == GameStatus.SECOND
                    color = CheckerColor.BLACK;
                    otherColor = CheckerColor.WHITE;
                    out = out2;
                    otherOut = out1;
                    in = in2;
                    playerIdWithMove = 1;
                }

                out.println("move-now/" + playerIdWithMove);
                otherOut.println("move-now/" + playerIdWithMove);

                try {
                    String request = in.readLine();
                    String[] tokens = request.split("/");

                    if (tokens[0].equals("move")) {
                        int x1 = Integer.parseInt(tokens[1]);
                        int y1 = Integer.parseInt(tokens[2]);
                        int x2 = Integer.parseInt(tokens[3]);
                        int y2 = Integer.parseInt(tokens[4]);

                        board.move(x1, y1, x2, y2, color);

                        CoordinatesArray tmp = board.getPossibleMove(x1, y1, x2, y2, color);
                        CoordinatesArray path = new CoordinatesArray();
                        path.add(x1, y1);
                        for (Coordinate coordinate : tmp.getList())
                            path.add(coordinate.getX(), coordinate.getY());

                        int turnIdInDatabase = databaseManager.addNewTurn(gameIdInDatabase, currentTurn, gameStatus == GameStatus.WHITE_TURN ? CheckerColor.WHITE : CheckerColor.BLACK);
                        currentTurn += 1;
                        int movesCounter = 0;

                        for (int i = 1; i < path.size(); i++) {
                            Coordinate last = path.getList().get(i - 1);
                            Coordinate now = path.getList().get(i);

                            CustomClock.waitMillis(200);

                            out.println("update-piece-position/" + last.getX() + "/" + last.getY() + "/" + now.getX() + "/" + now.getY());
                            otherOut.println("update-piece-position/" + last.getX() + "/" + last.getY() + "/" + now.getX() + "/" + now.getY());

                            int dx = now.getX() > last.getX() ? 1 : -1;
                            int dy = now.getY() > last.getY() ? 1 : -1;
                            int steps = Math.abs(now.getX() - last.getX());

                            boolean wasBeat = false;
                            for (int x = last.getX(), y = last.getY(), iter = 0; iter < steps; iter++, x += dx, y += dy) {
                                if (board.coordinateIsWithPiece(x, y, otherColor)) {
                                    board.removePiece(x, y);
                                    out.println("remove-piece/" + x + "/" + y);
                                    otherOut.println("remove-piece/" + x + "/" + y);
                                    wasBeat = true;
                                }
                            }

                            databaseManager.addNewMove(turnIdInDatabase, movesCounter, wasBeat, last.getX(), last.getY(), now.getX(), now.getY());
                            movesCounter += 1;

                            if (i != path.size() - 1)
                                CustomClock.waitMillis(300);
                        }

                        if (board.isKing(x2, y2)) {
                            out.println("update-piece-to-king/" + x2 + "/" + y2);
                            otherOut.println("update-piece-to-king/" + x2 + "/" + y2);
                        }

                        board.generatePossibleMoves();
                        out1.println("possible-moves" + preparePossibleMoves(CheckerColor.WHITE));
                        out2.println("possible-moves" + preparePossibleMoves(CheckerColor.BLACK));

                        String gameResult = board.whoWins();
                        if (gameResult != null) {
                            out1.println(gameResult);
                            out2.println(gameResult);
                            break;
                        }

                        if (gameStatus == GameStatus.WHITE_TURN)
                            gameStatus = GameStatus.BLACK_TURN;
                        else
                            gameStatus = GameStatus.WHITE_TURN;
                    }
                }
                catch (NullPointerException nullPointerException) {
                    if(color == CheckerColor.WHITE)
                        otherOut.println("black");
                    else
                        otherOut.println("white");

                    break;
                }
            }

            player1.close();
            player2.close();

            System.out.println("Game finished");
        }
        catch(Exception ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}