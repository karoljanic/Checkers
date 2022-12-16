package org.checkers;

import java.io.*;
import java.net.*;
import java.util.*;

import org.checkers.boards.*;
import org.checkers.utils.CheckerColor;
import org.checkers.utils.GameType;

public class MainServer
{
    private static final HashMap<GameType, ArrayList<Socket>> waitingForGame = new HashMap<>();

    private static void startGame(GameType type) {
        if (waitingForGame.get(type).size() >= 2) {
            Board board = switch (type) {
                case INTERNATIONAL -> new InternationalBoard();
                case BRAZILIAN -> new BrazilianBoard();
                case THAI -> new ThaiBoard();
            };

            try {
                PrintWriter out0 = new PrintWriter(waitingForGame.get(type).get(0).getOutputStream(), true);
                BufferedReader in0 = new BufferedReader(new InputStreamReader(waitingForGame.get(type).get(0).getInputStream()));

                PrintWriter out1 = new PrintWriter(waitingForGame.get(type).get(1).getOutputStream(), true);
                BufferedReader in1 = new BufferedReader(new InputStreamReader(waitingForGame.get(type).get(1).getInputStream()));

                int r = new Random().nextInt(2);

                if(r == 0) {
                    out0.println("set-id/" + CheckerColor.WHITE.ordinal());
                    out1.println("set-id/" + CheckerColor.BLACK.ordinal());
                }
                else {
                    out0.println("set-id/" + CheckerColor.BLACK.ordinal());
                    out1.println("set-id/" + CheckerColor.WHITE.ordinal());
                }

                out0.println("init-board/8/4/1/0/3/0/5/0/7/0/0/7/2/7/4/7/6/7");
                out1.println("init-board/8/4/1/7/3/7/5/7/7/7/0/0/2/0/4/0/6/0");

                out0.println("possible-moves/0/7/1/6");
                out1.println("possible-moves/1/7/0/6/1/7/2/6");

            } catch (Exception exception) {
                System.out.println("Wow! Exception: " + exception.getMessage());
            }

            //new CheckersGame(waitingForGame.get(type).get(0), waitingForGame.get(type).get(1), board).start();
            
           // waitingForGame.get(type).remove(0);
            // waitingForGame.get(type).remove(0);
            System.out.println("Started new game type " + type.toString());
        }
    }

    public static void main(String[] args)
    {
        waitingForGame.put(GameType.INTERNATIONAL, new ArrayList<>());
        waitingForGame.put(GameType.BRAZILIAN, new ArrayList<>());
        waitingForGame.put(GameType.THAI, new ArrayList<>());

        try (ServerSocket serverSocket = new ServerSocket(4444))
        {
            System.out.println("Server is listening on port 4444");

            while (true)
            {
                Socket socket = serverSocket.accept();

                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String inComm = in.readLine();
                System.out.println(inComm);

                String[] command = inComm.split("/");
                System.out.println(Arrays.toString(command));

                if(command.length == 2 && Objects.equals(command[0], "game-type")) {
                    GameType type = GameType.valueOf(command[1]);

                    System.out.println("SOCKET: " + socket.toString());
                    System.out.println("New client connected: " + type.toString());

                    waitingForGame.get(type).add(socket);

                    out.println("PODLACZONO CIE");

                    startGame(type);
                }
            }

        }
        catch (final IOException ex)
        {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}