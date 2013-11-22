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
public interface GraphicEngine {
    void addSoldier ( Soldier soldier ); 
    void updateSoldier ( Soldier soldier );
    void destroySoldier ( Soldier soldier );
    
    void addPlanet ( Planet planet );
    void updatePlanet ( Planet planet ) ;
    
    void setTeam1Name ( String name ) ;
    void setTeam2Name ( String name ) ;
    
    void setSize ( int width , int height );
}
