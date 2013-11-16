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
public class Soldier {
    private SoldierType type;

    public Soldier(SoldierType type) {
        this.type = type;
    }

    public SoldierType getType() {
        return type;
    }
}
