/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PlanetWars;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author mohammad
 */
public class World {
    
    Planet [] planets;
    ArrayList<Soldier> soldiers;
    int width;
    int height;
    GraphicEngine engine;
    
    public World (GraphicEngine engine){
        this.engine = engine;
        this.soldiers = new ArrayList<Soldier>();
        parseMapFile("default.map");
    }
    
    public World(String map,GraphicEngine engine){
        this.soldiers = new ArrayList<Soldier>();
        this.engine = engine;
        parseMapFile(map);    
    }
    
    public Planet[] getPlanets() {
        return planets;
    }
    
    public ArrayList<Soldier> getSoldiers() {
        return soldiers;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
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
        
        setWidth(sc.nextInt()); 
        setHeight(sc.nextInt()); 
        
        engine.setSize(getWidth(), getHeight());
        
        Team team1 = new Team(sc.next());
        Team team2 = new Team(sc.next());
        
        engine.setTeamNames(team1.getName(),team2.getName());
        
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
                planets[i]=new Planet(dia, new Point(x, y));
            else
                planets[i] = new Planet(dia, new Team(team), soldiers, new Point(x, y));
            engine.addPlanet(planets[i]);
        }
    }
    
    public void sendSoldier( int planet1 , int planet2, int nr){
        Planet p1 = planets[planet1];
        Planet p2 = planets[planet2];
        
        int nrOfCurrentSolInPlanets = p1.getNumerOfSoldiers() ;
        
        if ( nrOfCurrentSolInPlanets < nr)
            return;
        
        p1.setNumerOfSoldiers(nrOfCurrentSolInPlanets-nr);
        Soldier newSoldier = new Soldier(p1.getOwner(), p1.getPosition(), p2 , nr);
        soldiers.add(newSoldier);
        engine.addSoldier(newSoldier);
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
    
    
    public void Step(){
        for (int i = 0; i < planets.length; i++) {
            planets[i].Step();
            engine.updatePlanet(planets[i]);
        }
        for (int i = 0; i < soldiers.size(); i++) {
            soldiers.get(i).setNewPos();
            System.out.println(soldiers.get(i).getPosition());
            engine.updateSoldier(soldiers.get(i));
        }
        destroyUselessSoldiers();
    }
    
    public void destroyUselessSoldiers(){
        for (int i = 0; i < soldiers.size(); i++) {
            Soldier sol = soldiers.get(i);
            if ( sol.isArrived() ){
                int nr = soldiers.get(i).strenght;
                int nrOfCurrentSolsInDest = sol.dest.getNumerOfSoldiers();
                if ( sol.team.equals(sol.dest.getOwner()) 
                        || sol.dest.getOwner().equals("none") ){
                   sol.dest.setNumerOfSoldiers(nr+nrOfCurrentSolsInDest);
                } else {
                    int newNumber = nrOfCurrentSolsInDest - nr ;
                    if ( newNumber > 0 )
                        sol.dest.setNumerOfSoldiers(newNumber);
                    else if ( newNumber < 0 ){
                        sol.dest.setNumerOfSoldiers(-newNumber);
                        sol.dest.setOwner(sol.team);
                    } else {
                        sol.dest.setNumerOfSoldiers(0);
                        sol.dest.setOwner(new Team("none"));
                    }
                }
                engine.updatePlanet(sol.dest);
                engine.destroySoldier(sol);
                soldiers.remove(sol);
            }
        }
    }
    
    @Override
    public String toString(){
        String ret = "$";
        for (int i = 0; i < planets.length; i++) {
            ret+=Integer.toString(i+1) + " : "+ planets[i].toString() + "\n";
        }
        for (int i = 0; i < soldiers.size(); i++) {
            ret+=soldiers.get(i).toString() + "\n";
        }
        ret+="$";
        return ret;
    }
}
