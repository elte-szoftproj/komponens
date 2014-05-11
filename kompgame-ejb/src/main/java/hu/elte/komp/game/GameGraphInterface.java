/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp.game;

import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Zsolt
 */
public interface GameGraphInterface {

    /**
     * Visszaadja az aktualis jatekallas objektumat
     * @return
     */
    Object getCurrentStep();

    /**
     * Visszaadja a lehetseges lepesek listajat az adott
     * @param step
     * @return
     */
    Iterable getPossibleSteps(Object step);

    /**
     * Megmondja, mekkora az adott erteke, ha a jatekos AI. Ha human, 0-val ter vissza.
     * @param step
     * @return
     */
    long getStepScoreForCurrentPlayer(Object step);
    
}
