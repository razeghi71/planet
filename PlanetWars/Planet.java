/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PlanetWars;

/**
 *
 * @author mohammad
 */
public class Planet {
    private double diameter;
    private Team owner;
    private int numberOfSoldiers;
    
    // upgrade parameteres
    private static double upgradeRate = 0.2; 
    private static int minUpgradeSoldiers = 40 ;

    public Planet(double diameter) {
        this.diameter = diameter;
    }

    public Planet(double diameter, Team owner, int numberOfSoldiers) {
        this.diameter = diameter;
        this.owner = owner;
        this.numberOfSoldiers = numberOfSoldiers;
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
    
    
    public void sendSoldierTo ( Planet other , int numberOfSoldiers ){
        // here Graphic Engine should call 
        int newNumberOfSoldiers = other.getNumerOfSoldiers() - getNumerOfSoldiers();
        other.setNumerOfSoldiers(Math.abs(newNumberOfSoldiers));
        if ( newNumberOfSoldiers < 0 )
            other.setOwner(getOwner());
    }
}
