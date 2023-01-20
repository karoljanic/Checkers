package org.checkers.games;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

import org.checkers.Bot;
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
 * klasa obsługuje grę między klientem a botem
 */
public class BotCheckersGame extends CheckerGame {
    /**
     * połączenie z klientem
     */
    private final Socket player;
    /**
     * bot wykonujący ruchy
     */
    private final Bot bot;

    /**
     * true, jeśli bot ma białe pionki
     */
    private final boolean isBotWhite;

    /**
     * @param player połączenie z klientem
     * @param board plansza do gry
     * konstruktor ustawia niezbędne paramatery dla nowego obiektu
     */
    public BotCheckersGame(Socket player, GameType gameType, Board board, DatabaseManager databaseManager) {
        super(gameType, board, databaseManager);

        isBotWhite = new Random(System.currentTimeMillis()).nextBoolean();
        if (isBotWhite)
            this.bot = new Bot(board, CheckerColor.WHITE);
        else
            this.bot = new Bot(board, CheckerColor.BLACK);

        this.player = player;
        gameStatus = GameStatus.WHITE_TURN;

        gameIdInDatabase = databaseManager.addNewGame(gameType);
        currentTurn = 0;
    }

    /**
     * funkcja komunikuje się z klientem nadzorując przebieg gry
     */
    @Override
    public void run() {
        try {
            board.generatePossibleMoves();

            PrintWriter out = new PrintWriter(player.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(player.getInputStream()));
            
            CheckerColor playerColor = isBotWhite ? CheckerColor.BLACK : CheckerColor.WHITE;
            CheckerColor botColor = isBotWhite ? CheckerColor.WHITE : CheckerColor.BLACK;

            out.println("set-id/" + playerColor.ordinal());
            out.println("init-board" + prepareBoardDescription());
            out.println("possible-moves" + preparePossibleMoves(playerColor));

            int playerId = playerColor.ordinal();

            while(gameStatus != GameStatus.FINISHED) {
                try {
                    if ((gameStatus == GameStatus.BLACK_TURN && isBotWhite) || (gameStatus == GameStatus.WHITE_TURN && !isBotWhite)) {
                        out.println("move-now/" + playerId);
                        String request = in.readLine();
                        String[] tokens = request.split("/");

                        if (tokens[0].equals("move")) {
                            int x1 = Integer.parseInt(tokens[1]);
                            int y1 = Integer.parseInt(tokens[2]);
                            int x2 = Integer.parseInt(tokens[3]);
                            int y2 = Integer.parseInt(tokens[4]);

                            board.move(x1, y1, x2, y2, playerColor);

                            CoordinatesArray tmp = board.getPossibleMove(x1, y1, x2, y2, playerColor);
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

                                int dx = now.getX() > last.getX() ? 1 : -1;
                                int dy = now.getY() > last.getY() ? 1 : -1;
                                int steps = Math.abs(now.getX() - last.getX());

                                boolean wasBeat = false;
                                for (int x = last.getX(), y = last.getY(), iter = 0; iter < steps; iter++, x += dx, y += dy) {
                                    if (board.coordinateIsWithPiece(x, y, botColor)) {
                                        board.removePiece(x, y);
                                        out.println("remove-piece/" + x + "/" + y);
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
                            }

                            board.generatePossibleMoves();
                            out.println("possible-moves" + preparePossibleMoves(botColor));
                        }
                    }
                    else { //now bot moves
                        CoordinatesArray botsMove = bot.makeMove();
                        CustomClock.waitMillis(400);

                        int x1 = botsMove.getList().get(0).getX();
                        int y1 = botsMove.getList().get(0).getY();
                        int x2 = botsMove.getList().get(botsMove.getList().size() - 1).getX();
                        int y2 = botsMove.getList().get(botsMove.getList().size() - 1).getY();
                        botsMove.getList().remove(0);

                        board.move(x1, y1, x2, y2, botColor);

                        CoordinatesArray tmp = board.getPossibleMove(x1, y1, x2, y2, botColor);
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

                            out.println("update-piece-position/" + last.getX() + "/" + last.getY() + "/" + now.getX() + "/" + now.getY());

                            int dx = now.getX() > last.getX() ? 1 : -1;
                            int dy = now.getY() > last.getY() ? 1 : -1;
                            int steps = Math.abs(now.getX() - last.getX());

                            CustomClock.waitMillis(200);

                            boolean wasBeat = false;
                            for (int x = last.getX(), y = last.getY(), iter = 0; iter < steps; iter++, x += dx, y += dy) {
                                if (board.coordinateIsWithPiece(x, y, playerColor)) {
                                    board.removePiece(x, y);
                                    out.println("remove-piece/" + x + "/" + y);
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
                        }

                        board.generatePossibleMoves();
                        out.println("possible-moves" + preparePossibleMoves(playerColor));

                    }
                        
                    String gameResult = board.whoWins();
                    if (gameResult != null) {
                        out.println(gameResult);
                        break;
                    }

                    if (gameStatus == GameStatus.WHITE_TURN)
                        gameStatus = GameStatus.BLACK_TURN;
                    else
                        gameStatus = GameStatus.WHITE_TURN;
                }
                catch (NullPointerException ex) {
                    System.out.println(ex.getMessage());
                }
            }

            player.close();

            System.out.println("Game finished");
        }
        catch(Exception ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}