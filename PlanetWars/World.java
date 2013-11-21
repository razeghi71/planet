/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PlanetWars;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author mohammad
 */
public class World {
    
    Planet [] planets;
    GraphicEngine engine;
    int width;
    int height;
    
    public World (GraphicEngine engine){
        parseMapFile("default.map");
        this.engine = engine;
    }
    
    public World(String map,GraphicEngine engine){
        parseMapFile(map);
        this.engine = engine;
                
    }
    
    private void parseMapFile (String map) {
        FileInputStream inp;
        try {
            inp = new FileInputStream(map);
        } catch (FileNotFoundException ex) {
            parseMapFile("default.map");
            return;
        }
        Scanner sc = new Scanner(inp);
        
        width = sc.nextInt();
        height = sc.nextInt();
        
        Team team1 = new Team(sc.next());
        Team team2 = new Team(sc.next());
        
        int numberOfPlanets = sc.nextInt();
        
        planets = new Planet[numberOfPlanets];
        int x;
        int y;
        int dia;
        String team;
        int soldiers;
        
        for (int i = 0; i < numberOfPlanets; i++) {
            x = sc.nextInt();
            y = sc.nextInt();
            dia = sc.nextInt();
            team = sc.next();
            soldiers = sc.nextInt();
            
            if ( team.equals("none") )
                planets[i]=new Planet(dia, new Point(x, y), engine);
            else
                planets[i] = new Planet(dia, team2, soldiers, new Point(x, y), engine);
        }
    }
    
    public void sendSoldier( int planet1 , int planet2, int nr){
        planets[planet1].sendSoldierTo(planets[planet2], nr);
    }
    
    public boolean isGameFinished (){
        
        for (int i = 1; i < planets.length ; i++) {
            if ( !planets[i].getOwner().equals(planets[i-1].getOwner()) )
                return false;
        }
        return true;
    }

    public String getCompleteWorldInfo (){
        return toString();
    }
    
    @Override
    public String toString(){
        String ret = "";
        for (int i = 0; i < planets.length; i++) {
            ret+=Integer.toString(i+1) + " : "+ planets[i].toString() + "\n";
        }
        return ret;
    }
    
}
