package PlanetWars;

import Graphic.GraphicalWorld;

/**
 *
 * @author mohammad
 */
public class Main {


    public static void main(String[] args) {
    	GraphicEngine engine = new GraphicalWorld();
        
        Game g = new Game(4000, "/resources/default.map", engine);
        System.out.println("Listening On Port 0.0.0.0:4000");
        
        g.doSim();
    }
    
}
 