package PlanetWars;

import java.awt.Point;


/**
 *
 * @author mohammad
 */
public class Planet {
    private int id  = 0;
    private int diameter = 0;
    private Team owner ;
    private int numberOfSoldiers = 0;
    private Point position;
    public static int SoldierCreateRate = 30 ;
    
    public Planet(int diameter, Point position, int id) {
        this.diameter = diameter;
        this.position = position;
        this.owner = new Team("none");
        this.numberOfSoldiers = 0;
        this.id = id;
    }

    public Planet(int diameter, Team owner, int numberOfSoldiers, Point position,int id) {
        this.diameter = diameter;
        this.owner = owner;
        this.numberOfSoldiers = numberOfSoldiers;
        this.position = position;
        this.id = id;
    }
    
    public int getDiameter() {
        return diameter;
    }    

    public Team getOwner() {
        return owner;
    }

    public void setOwner(Team owner) {
        this.owner = owner;
    }

    public int getNumerOfSoldiers() {
        return numberOfSoldiers;
    }

    public void setNumerOfSoldiers(int numberOfSoldiers) {
        this.numberOfSoldiers = numberOfSoldiers;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
    
    /**
     * add number of Soldiers based on diameter
     */
    public void Step(){
        if ( !this.owner.getName().equals("none") && !this.owner.getName().equals("Blackhole")  ) {
            this.numberOfSoldiers+=this.diameter/SoldierCreateRate;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    @Override
    public String toString(){
        return "{\n"
                + "id: " + Integer.toString(id)  + " ,\n"
                + "dia: " + Integer.toString(diameter)  + " ,\n"
                + "owner: " + owner.getName() + " ,\n"
                + "soldiers: " + Integer.toString(numberOfSoldiers) + " ,\n"
                + "x: " + Integer.toString(position.x) + " ,\n"
                + "y: " + Integer.toString(position.y) + "\n"
                + "}"
                ;
    }
}
