package PlanetWars;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Random;
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
    private int messgeCycle = 250;
    private GraphicEngine engine;
    ArrayList<ArrayList<String>> msgList = new ArrayList<ArrayList<String> > () ;
    
    Object write_lock = new Object() ;
    
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

      
        for (int i = 0; i < 2; i++) 
        	msgList.add(new ArrayList<String>());


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
            world.setTeamNames(teams[0], teams[1]);
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }
    
    void doMsg(String msg, int localI){
    	Planet[] p = world.getPlanets();
    	String[] parts = msg.split(" ");
        if (parts.length == 3) {
            try {
                int from = Integer.parseInt(parts[0]) - 1 ;
                int to = Integer.parseInt(parts[1]) - 1;
                int nr = Integer.parseInt(parts[2]);

                if (from >= 0 && to >= 0 && from < p.length && to < p.length
                        && teams[localI].equals(p[from].getOwner())
                        && nr > 0) {
                    world.sendSoldier(from, to, nr);
                }
            } catch (NumberFormatException nfe) {
            }
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
                    
                    while (!world.isGameFinished()) {
                        String msg;
                        try {
                            msg = reader[localI].nextLine();
                        } catch (NoSuchElementException exp) {
                            break;
                        }
                        synchronized (msgList.get(localI)) {
                        	msgList.get(localI).add(msg) ;
						}
                    }
                }
            });
            ReaderThread[i].start();
        }

         
        Thread[] writerThread = new Thread[2];
        for (int i = 0; i < 2; i++) {
            final int localI = i;
            
            writerThread[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                	while(true){
	                	//wait for write_lock 
	                	try {
	                		synchronized (write_lock) {
	                			write_lock.wait();	
							}
							
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
	                	final String localInfo = world.getCompleteWorldInfo();
	                    writer[localI].println(localInfo);
	                    System.err.println("WRITING------------------------ : " + localI + " " + (System.currentTimeMillis()));
	                    writer[localI].flush();
                	}
                }
            });
            writerThread[i].start();
        }
        
        
        int our_time = 0 ;
        while (!world.isGameFinished()) {
        	
        	int tmp = (messgeCycle / updateCycle);
        	System.err.println("Time is : " + our_time + " m/u: " + tmp + " real time is : " + (System.currentTimeMillis()));
        	
        	our_time = 0;
        	
        	if (our_time % tmp == 0){
        		synchronized (write_lock) {
        			write_lock.notifyAll();
        		}
        	}
            
        	
        	//TODO step tartib 2rost she :D
        	
        	 
        	if (our_time % tmp == 0){
	        	for(int i = 0 ; i < 2 ; i++){
	        		synchronized (msgList.get(i)) {
	        			if (msgList.get(i).size() > 0){
	        				doMsg(msgList.get(i).get(0), i);
	        				msgList.get(i).remove(0);
	        			}
					}
	        	}
        	}
        	
        	world.Step();
        		
        	our_time++ ;
        	
        	try {
                Thread.sleep(messgeCycle);
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
