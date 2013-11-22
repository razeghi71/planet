package PlanetWars;

import java.awt.Point;


/**
 *
 * @author mohammad
 */
public class Planet {
    private int diameter;
    private Team owner ;
    private int numberOfSoldiers = 0;
    private Point position;
    public static int SoldierCreateRate = 30 ;
    
    public Planet(int diameter, Point position) {
        this.diameter = diameter;
        this.position = position;
        this.owner = new Team("none");
        this.numberOfSoldiers = 0;
    }

    public Planet(int diameter, Team owner, int numberOfSoldiers, Point position) {
        this.diameter = diameter;
        this.owner = owner;
        this.numberOfSoldiers = numberOfSoldiers;
        this.position = position;
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
    

    public void Step(){
        if ( !this.owner.getName().equals("none") ) {
            this.numberOfSoldiers+=this.diameter/SoldierCreateRate;
        }
    }
    
    @Override
    public String toString(){
        return "{\n"
                + "dia : " + Integer.toString(diameter)  + ",\n"
                + "owner : " + owner.getName() + ",\n"
                + "soldiers : " + Integer.toString(numberOfSoldiers) + ",\n"
                + "x : " + Integer.toString(position.x) + ",\n"
                + "y : " + Integer.toString(position.y) + "\n"
                + "}"
                ;
    }
}
