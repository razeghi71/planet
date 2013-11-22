/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PlanetWars;

import java.awt.Point;

import Graphic.GraphicalWorld;

/**
 *
 * @author mohammad
 */
public class Main {

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
    	GraphicEngine engine = new GraphicalWorld();
        Game g = new Game(4000, "/resources/default.map", engine);
        g.doSim();
//    	g.setSize(500, 600);
//    	g.setTeamNames("Kossher1", "Kossher2");
//    	
//    	Planet planet = new Planet(100, new Point(100, 100));
//    	planet.setOwner(new Team("None"));
//    	g.addPlanet(planet);
//    	
//    	for (int i = 0 ; i < 100 ; i ++) {
//    		try {
//    			Thread.sleep(10);
//    		} catch (InterruptedException e) {
//    			// TODO Auto-generated catch block
//    			e.printStackTrace();
//    		}
//        	planet.setOwner(new Team("Kossher"+ i%2));
//        	planet.setPosition(new Point(100+i, 100+i));
//        	g.updatePlanet(planet);
//    	}
    }
    
}
 