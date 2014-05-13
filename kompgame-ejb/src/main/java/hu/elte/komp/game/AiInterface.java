/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp.game;

/**
 *
 * @author Zsolt
 */
public interface AiInterface {
    
    /**
     * Megadja az AI kovetkezo lepeset. Ha nullal, vagy ervenytelennel ter vissza,
     * az AI feladja.
     * 
     * @param gameGraph
     * @param youArePlayerOne igaz ha az AI-val lepo jatek az elso jatekos
     * @return 
     */
    public Object getNextStep(GameGraphInterface gameGraph, boolean youArePlayerOne);
    
}
