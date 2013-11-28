package PlanetWars;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mohammad
 */
public class Game {

    private PrintWriter writer[];
    private Scanner reader[];
    private Socket sock[];
    private World world;
    private String teams[] = new String[2];
    private int updateCycle = 50;
    private int messgeCycle = 1000;
    private GraphicEngine engine;

    /**
     * Game Class
     *
     * @param port which port to listen on
     * @param map map file
     * @param engine game graphic engine
     */
    public Game(int port, String map, GraphicEngine engine) {
        this.engine = engine;
        this.writer = new PrintWriter[2];
        this.reader = new Scanner[2];
        this.sock = new Socket[2];
        world = new World(map, engine);

        try {
            ServerSocket ss = new ServerSocket(port);

            for (int i = 0; i < 2; i++) {
                try {
                    sock[i] = ss.accept();
                    writer[i] = new PrintWriter(sock[i].getOutputStream());
                    reader[i] = new Scanner(sock[i].getInputStream());
                    teams[i] = reader[i].next();
                } catch (IOException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }

    /**
     * Run Game
     */
    public void doSim() {

        Thread[] ReaderThread = new Thread[2];
        for (int i = 0; i < 2; i++) {
            final int localI = i;
            ReaderThread[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    Planet[] p = world.getPlanets();
                    while (!world.isGameFinished()) {
                        String msg;
                        try {
                            msg = reader[localI].nextLine();
                        } catch (NoSuchElementException exp) {
                            break;
                        }
                        String[] parts = msg.split(" ");
                        if (parts.length == 3) {
                            try {
                                int from = Integer.parseInt(parts[0]);
                                int to = Integer.parseInt(parts[1]);
                                int nr = Integer.parseInt(parts[2]);

                                if (from > 0 && to > 0 && from < p.length && to < p.length
                                        && teams[localI].equals(p[from - 1].getOwner().getName())
                                        && nr > 0) {
                                    world.sendSoldier(from - 1, to - 1, nr);
                                }
                            } catch (NumberFormatException nfe) {
                            }
                        }
                        try {
                            Thread.sleep(messgeCycle);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
            ReaderThread[i].start();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!world.isGameFinished()) {

                    String info = world.getCompleteWorldInfo();
                    Thread[] writerThread = new Thread[2];
                    for (int i = 0; i < 2; i++) {
                        final int localI = i;
                        final String localInfo = info;
                        writerThread[i] = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                writer[localI].println(localInfo);
                                writer[localI].flush();
                            }
                        });
                        writerThread[i].start();
                    }
                    try {
                        writerThread[0].join();
                        writerThread[1].join();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    try {
                        Thread.sleep(messgeCycle);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();

        while (!world.isGameFinished()) {
            world.Step();
            engine.setGameInfo(world.getNumberOfSoldiers(teams[0]),
                    world.getNumberOfSoldiers(teams[1]));
            try {
                Thread.sleep(updateCycle);
            } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        engine.gameFinishedEvent("Game Finished :) ");
        for (int i = 0; i < 2; i++) {
            writer[i].println("%");
            writer[i].flush();
            try {
                sock[i].close();
            } catch (IOException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
