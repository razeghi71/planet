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
    private Team team;
    private Point position;
    private Planet dest;
    private int strenght;
    private long number ; 

    public static int soldierSpeed =  8;
    
    private boolean arrived = false;
    
    public Soldier(Team team, Point position, Planet dest, int strenght, long number) {
        this.team = team;
        this.position = position;
        this.dest = dest;
        this.strenght = strenght;
        this.number = number;
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
       // Using Tales To Calc New Pos Based On Speed
       double dist = Math.sqrt ( ( position.x - dest.getPosition().x )*( position.x - dest.getPosition().x ) 
               + ( position.y - dest.getPosition().y )*( position.y - dest.getPosition().y ) ) ;
       
       
       double ratio = dist/soldierSpeed;
       
       
       int newX = (int) (( dest.getPosition().x - position.x  ) /ratio) + position.x;
       int newY = (int) (( dest.getPosition().y - position.y  ) /ratio) + position.y;
       
        setPosition(new Point(newX, newY));
    
        double newDist = Math.sqrt ( (position.x - dest.getPosition().x )*( position.x - dest.getPosition().x ) 
               + ( position.y - dest.getPosition().y )*( position.y - dest.getPosition().y ) ) ;
       
        if ( newDist < dest.getDiameter() ){
            arrived = true ;
        }
    }

    public boolean isArrived() {
        return arrived;
    }

    @Override
    public String toString() {
        return "{\n" 
                + "num:" + number + ",\n"
                + "team:" + team + ",\n"
                + "posX:" + position.x + ",\n"
                + "posY:" + position.y + ",\n"
                + "destX:" + dest.getPosition().x + ",\n"
                + "destY:" + dest.getPosition().y + ",\n"
                + "strength:" + strenght +  "\n"
                + "}";
    }
    
    
}
