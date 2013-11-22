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
public class Soldier {
    Team team;
    Point position;
    Planet dest;
    int strenght;

    public static int soldierSpeed =  10;
    
    private boolean arrived ;
    
    public Soldier(Team team, Point position, Planet dest, int strenght) {
        this.team = team;
        this.position = position;
        this.dest = dest;
        this.strenght = strenght;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Planet getDest() {
        return dest;
    }

    public void setDest(Planet dest) {
        this.dest = dest;
    }

    public int getStrenght() {
        return strenght;
    }

    public void setStrenght(int strenght) {
        this.strenght = strenght;
    }  
    
    public void setNewPos(){
       double dist = ( position.x - dest.getPosition().x )*( position.x - dest.getPosition().x ) 
               + ( position.y - dest.getPosition().y )*( position.x - dest.getPosition().x );
       
       double ratio = dist/soldierSpeed;
       
       int newX = (int) (( dest.getPosition().x - position.x  ) *ratio) + dest.getPosition().x;
       int newY = (int) (( dest.getPosition().y - position.y  ) *ratio) + dest.getPosition().y;
       
        setPosition(new Point(newX, newY));
    
        double newDist = ( position.x - dest.getPosition().x )*( position.x - dest.getPosition().x ) 
               + ( position.y - dest.getPosition().y )*( position.x - dest.getPosition().x );
       
        if ( newDist < dest.getDiameter() ){
            arrived = true ;
        }
        
    }

    public boolean isArrived() {
        return arrived;
    }
}
