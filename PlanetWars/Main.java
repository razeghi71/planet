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
    }
    
}
 