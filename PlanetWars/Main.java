package PlanetWars;

import Graphic.GraphicalWorld;

/**
 *
 * @author mohammad
 */
public class Main {


    public static void main(String[] args) {
    	GraphicEngine engine = new GraphicalWorld();
        Game g = null;
        if ( args.length == 1 )
            g = new Game(4000, args[0], engine);
        else{
            System.err.println("usage : \n java -jar Planet.jar mapfile" );
            System.exit(1);
        }
            
        System.out.println("Listening On Port 0.0.0.0:4000");

        g.doSim();
    }
    
}
 