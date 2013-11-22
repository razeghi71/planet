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
    long soldierNum = 0 ;
    
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
        int nrSoldiers;
        
        for (int i = 0; i < numberOfPlanets; i++) {
            x = sc.nextInt();
            y = sc.nextInt();
            dia = sc.nextInt();
            team = sc.next();
            nrSoldiers = sc.nextInt();
            
            if ( team.equals("none") )
                planets[i]=new Planet(dia, new Point(x, y));
            else
                planets[i] = new Planet(dia, new Team(team), nrSoldiers, new Point(x, y));
            engine.addPlanet(planets[i]);
        }
    }
    
    public void sendSoldier( int planet1 , int planet2, int nr){
        if ( planet1 >= planets.length || planet2 >= planets.length )
            return;
        
        Planet p1 = planets[planet1];
        Planet p2 = planets[planet2];
        
        int nrOfCurrentSolInPlanets = p1.getNumerOfSoldiers() ;
        
        if ( nrOfCurrentSolInPlanets < nr)
            return;
        
        p1.setNumerOfSoldiers(nrOfCurrentSolInPlanets-nr);
        Soldier newSoldier = new Soldier(p1.getOwner(), p1.getPosition(), p2 , nr,soldierNum++);
        soldiers.add(newSoldier);
        engine.addSoldier(newSoldier);
    }
    
    public boolean isGameFinished (){
        for (int i = 1; i < planets.length ; i++) {
            if ( !planets[i].getOwner().equals(planets[i-1].getOwner()) )
                if (!planets[i-1].getOwner().equals("none"))
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
            engine.updateSoldier(soldiers.get(i));
        }
        destroyUselessSoldiers();
    }
    
    public void destroyUselessSoldiers(){
        for (int i = 0; i < soldiers.size(); i++) {
            Soldier sol = soldiers.get(i);
            if ( sol.isArrived() ){
                int nr = soldiers.get(i).getStrenght();
                int nrOfCurrentSolsInDest = sol.getDest().getNumerOfSoldiers();
                if ( sol.getTeam().equals(sol.getDest().getOwner()) 
                        || sol.getDest().getOwner().equals("none") ){
                   sol.getDest().setNumerOfSoldiers(nr+nrOfCurrentSolsInDest);
                } else {
                    int newNumber = nrOfCurrentSolsInDest - nr ;
                    if ( newNumber > 0 )
                        sol.getDest().setNumerOfSoldiers(newNumber);
                    else if ( newNumber < 0 ){
                        sol.getDest().setNumerOfSoldiers(-newNumber);
                        sol.getDest().setOwner(sol.getTeam());
                    } else {
                        sol.getDest().setNumerOfSoldiers(0);
                        sol.getDest().setOwner(new Team("none"));
                    }
                }
                engine.updatePlanet(sol.getDest());
                engine.destroySoldier(sol);
                soldiers.remove(sol);
            }
        }
    }
    
    @Override
    public String toString(){
        String ret = "$";
        for (int i = 0; i < planets.length; i++) {
            ret+=Integer.toString(i+1) + ": "+ planets[i].toString() + "\n";
        }
        ret += "#";
        for (int i = 0; i < soldiers.size(); i++) {
            ret+=soldiers.get(i).toString() + "\n";
        }
        return ret;
    }
}
