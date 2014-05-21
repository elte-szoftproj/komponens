/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp.game;

import java.util.Iterator;
import java.util.Set;

public interface GameGraphInterface {

    /**
     * Visszaadja az aktualis jatekallas objektumat (meg azelott, hogy az AI lepett volna)
     * @return
     */
    Object getCurrentStep();

    /**
     * Visszaadja a lehetseges lepesek listajat az adott lepesbol. 
     * A lepesfaban a ket jatekos lepesei felvaltva kovetik egymast.
     * @param step
     * @return
     */
    Iterable getPossibleSteps(Object step);

    /**
     * Megmondja, mekkora az adott erteke, ha a jatekos AI. Ha human, 0-val ter vissza.
     * @param step
     * @param forPlayerOne igaz, ha a kiertekelest az elso jatekos szempontjabol szeretnenk kerni, false egyebkent
     * @return
     */
    long getStepScoreForPlayer(Object step, boolean forPlayerOne);
    
}
