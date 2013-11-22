/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PlanetWars;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
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
    private World world;
    
    /**
     * Game Class
     * @param port which port to listen on
     * @param map map file
     */
    public Game(int port, String map,GraphicEngine engine)  {
        this.writer = new PrintWriter[2];
        this.reader = new Scanner[2];
        
        world = new World(map,engine);
       
        
        try {
            final ServerSocket ss = new ServerSocket(port);
        
            Thread[] t = new Thread[2];
            for (int i = 0; i < 2; i++) {
                final int localI = i ;
                t[i] = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Socket sock = ss.accept();
                            writer[localI] = new PrintWriter(sock.getOutputStream());
                            reader[localI] = new Scanner(sock.getInputStream());
                        } catch (IOException ex) {
                            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                t[i].run();
            }
            
            t[0].join();
            t[1].join();
        
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex){
        	Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * Run Game
     */
    public void run ( ){
        
        while (true){
            String info = world.getCompleteWorldInfo();
            Thread[] t = new Thread[2];
            for (int i = 0; i < 2; i++) {
                final int localI = i;
                final String localInfo = info;
                t[i] = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        writer[localI].println(localInfo);
                    }
                });
                t[i].run();
            }
            
            
            
            world.Step();
        }
    }
    
}
