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

    
    private Planet[] planets;
    private ArrayList<Soldier> soldiers;
    private int width;
    private int height;
    private GraphicEngine engine;
    private long soldierNum = 0;
    private int time = 0;
    private final int maxTime = 3600 ;
    private Team  teams[] = new Team[2];

    public World(GraphicEngine engine) {
        this.engine = engine;
        this.soldiers = new ArrayList<Soldier>();
        parseMapFile("default.map");
    }

    public World(String map, GraphicEngine engine) {
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

    /**
     * parse an input map file
     *
     * @param map map file to parse
     */
    private void parseMapFile(String map) {

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

        teams[0] = new Team(sc.next());
        teams[1] = new Team(sc.next());

        engine.setTeamNames(teams[0].getName(), teams[1].getName());

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

            if (team.equals("none")) {
                planets[i] = new Planet(dia, new Point(x, y),i+1);
            }
            else {
                planets[i] = new Planet(dia, new Team(team), nrSoldiers, new Point(x, y),i+1);
            }
            engine.addPlanet(planets[i]);
        }
        
        engine.setMaxClock(maxTime);

    }

    /**
     * send nr soldier from planet1 to planet2
     *
     * @param planet1 planet to send soldiers from
     * @param planet2 planet to send soldiers to
     * @param nr number of soldiers to send
     */
    public void sendSoldier(int planet1, int planet2, int nr) {
        if (planet1 >= planets.length || planet2 >= planets.length) {
            return;
        }

        Planet p1 = planets[planet1];
        Planet p2 = planets[planet2];

        int nrOfCurrentSolInPlanets = p1.getNumerOfSoldiers();

        if (nrOfCurrentSolInPlanets < nr) {
            return;
        }

        p1.setNumerOfSoldiers(nrOfCurrentSolInPlanets - nr);
        Soldier newSoldier = new Soldier(p1.getOwner(), p1.getPosition(), p2, nr, soldierNum++);
        synchronized (soldiers) {
            soldiers.add(newSoldier);
            engine.addSoldier(newSoldier);
        }
    }

    /**
     *
     * @return true if game is finished
     */
    public boolean isGameFinished() {   
        if (time > maxTime)
            return true;
        for (int i = 1; i < planets.length; i++)
            if (!planets[i].getOwner().getName().equals(planets[i - 1].getOwner().getName()))
                if ( !planets[i - 1].getOwner().getName().equals("none") && 
                        !planets[i - 1].getOwner().getName().equals("Blackhole") &&
                        !planets[ i ].getOwner().getName().equals("none") &&
                        !planets[ i ].getOwner().getName().equals("Blackhole")) 
                    return false;
        return true;
    }

    /**
     *
     * @return complete world info to send to clients
     */
    public String getCompleteWorldInfo() {
        return toString();
    }

    /**
     * Step Simulation To Next Step
     */
    public void Step() {
        for (int i = 0; i < planets.length; i++) {
            if ( !planets[i].getOwner().getName().equals("Blackhole"))
            {
                planets[i].Step();
                engine.updatePlanet(planets[i]);
            }
        }
        for (int i = 0; i < soldiers.size(); i++) {
            synchronized (soldiers) {
                soldiers.get(i).setNewPos();
            }
            engine.updateSoldier(soldiers.get(i));
        }
        destroyUselessSoldiers();
        time++;
        engine.setClock(time);
        engine.setGameInfo(getNumberOfSoldiers(teams[0].getName()),
                getNumberOfSoldiers(teams[1].getName()));
    }

    public void destroyUselessSoldiers() {
        outer : for (int i = 0; i < soldiers.size(); i++) {
            Soldier sol = soldiers.get(i);

            for (int j = 0; j < planets.length; j++) {
                if ( !planets[j].getOwner().getName().equals("Blackhole") )
                    continue;
                Point position = planets[j].getPosition();
                double dist = Math.sqrt ( (position.x - sol.getPosition().x )*( position.x - sol.getPosition().x ) 
               + ( position.y - sol.getPosition().y )*( position.y - sol.getPosition().y ) ) ;
                
                if ( dist < planets[j].getDiameter()/2.0 )
                {
                    engine.destroySoldier(sol);
                    soldiers.remove(sol);
                    continue outer;
                }
            }
            if (sol.isArrived()) {
                int nr = soldiers.get(i).getStrenght();
                int nrOfCurrentSolsInDest = sol.getDest().getNumerOfSoldiers();
                if (sol.getTeam().getName().equals(sol.getDest().getOwner().getName())) {
                    sol.getDest().setNumerOfSoldiers(nr + nrOfCurrentSolsInDest);
                } else {
                    int newNumber = nrOfCurrentSolsInDest - nr;
                    if (newNumber > 0) {
                        sol.getDest().setNumerOfSoldiers(newNumber);
                    } else if (newNumber < 0) {
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
    
    public int getNumberOfSoldiers (String team){
        int c = 0 ;
        for (int i = 0; i < planets.length; i++)
            if ( planets[i].getOwner().getName().equals(team) )
                c+=planets[i].getNumerOfSoldiers();
        return c;   
    }

    @Override
    public String toString() {
        String ret = "$";
        for (int i = 0; i < planets.length; i++) {
            ret += planets[i].toString() + "\n";
        }
        ret += "#";
        for (int i = 0; i < soldiers.size(); i++) {
            ret += soldiers.get(i).toString() + "\n";
        }
        return ret;
    }
}
