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

    public void setDiameter(double diameter) {
        this.diameter = diameter;
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
        //TODO : implement Soldier Send 
    }
    
}
