/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PlanetWars;

/**
 * each graphic Engine Should Implement this interface
 * @author mohammad
 */

public interface GraphicEngine {
    void sendSoldier ( Planet from , Planet to , int numberOfSoldiers);
}
