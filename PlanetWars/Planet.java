package PlanetWars;

import java.awt.Point;


/**
 *
 * @author mohammad
 */
public class Planet {
    private int id  = 0;
    private int diameter = 0;
    private String owner ;
    private int numberOfSoldiers = 0;
    private Point position;
    public static int SoldierCreateRate = 30 ;
    
    public Planet(int diameter, Point position, int id) {
        this.diameter = diameter;
        this.position = position;
        this.owner ="none";
        this.numberOfSoldiers = 0;
        this.id = id;
    }

    public Planet(int diameter, String owner, int numberOfSoldiers, Point position,int id) {
        this.diameter = diameter;
        this.owner = owner;
        this.numberOfSoldiers = numberOfSoldiers;
        this.position = position;
        this.id = id;
    }
    
    public int getDiameter() {
        return diameter;
    }    

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
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
        if ( !this.owner.equals("none") && !this.owner.equals("Blackhole")  ) {
            this.numberOfSoldiers += getGrowthRate();
        }
    }
    
    private int getGrowthRate(){
    	return this.diameter / Planet.SoldierCreateRate;
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
                + "owner: " + owner + " ,\n"
                + "soldiers: " + Integer.toString(numberOfSoldiers) + " ,\n"
                + "x: " + Integer.toString(position.x) + " ,\n"
                + "y: " + Integer.toString(position.y) + "\n"
                + "}"
                ;
    }
}
