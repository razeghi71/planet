/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
    
    // upgrade parameteres
    private static double upgradeRate = 0.2; 
    private static int minUpgradeSoldiers = 40 ;
    
    //Interface
    private GraphicEngine engine ;

    public Planet(int diameter, Point position, GraphicEngine graphicEngine) {
        this.diameter = diameter;
        this.position = position;
        this.engine = graphicEngine;
    }

    public Planet(int diameter, Team owner, int numberOfSoldiers, Point position, GraphicEngine graphicEngine) {
        this.diameter = diameter;
        this.owner = owner;
        this.numberOfSoldiers = numberOfSoldiers;
        this.position = position;
        this.engine = graphicEngine;
    }
    
    public double getDiameter() {
        return diameter;
    }
    
    public void upgradePlanet ( int numberOfSoldiersGive ){
        if ( numberOfSoldiersGive < minUpgradeSoldiers )
            return;
        if ( this.numberOfSoldiers < numberOfSoldiersGive )
            return;
        // Just a simple formula for calculating upgrade
        this.diameter+=upgradeRate*(numberOfSoldiersGive/minUpgradeSoldiers); 
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
        
    }
    
    @Override
    public String toString(){
        return "{\n"
                + "dia:" + Integer.toString(diameter)  + ",\n"
                + "owner:" + owner.getName() + ",\n"
                + "soldiers:" + Integer.toString(numberOfSoldiers) + ",\n"
                + "x:" + Integer.toString(position.x) + ",\n"
                + "y:" + Integer.toString(position.y) + "\n"
                + "}\n"
                ;
    }
}
