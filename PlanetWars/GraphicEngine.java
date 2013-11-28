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
    
    void setTeamNames ( String team1 , String team2 ) ;
    
    void setSize ( int width , int height );
    
    
    void setGameInfo (  int team1Soldiers , int team2Soldiers  );
    void gameFinishedEvent (String team);

    void setClock ( int clock );
    void setMaxClock ( int max);
}
