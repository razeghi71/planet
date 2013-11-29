package PlanetWars;

import java.awt.Point;  
/**
 *
 * @author mohammad
 */
public class Soldier {
    private String team;
    private Point position;
    private Planet dest;
    private int strenght;
    private long number ; 

    public static int soldierSpeed =  10;
    
    private boolean arrived = false;
    
    public Soldier(String team, Point position, Planet dest, int strenght, long number) {
        this.team = team;
        this.position = position;
        this.dest = dest;
        this.strenght = strenght;
        this.number = number;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
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
    
    /**
     * Calculate new pos of soldier
     */
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
                + "num: " + number + " ,\n"
                + "team: " + team + " ,\n"
                + "posX: " + position.x + " ,\n"
                + "posY: " + position.y + " ,\n"
                + "dest: " + dest.getId() + " ,\n"
                + "strength: " + strenght +  "\n"
                + "}";
    }
    
   
}
