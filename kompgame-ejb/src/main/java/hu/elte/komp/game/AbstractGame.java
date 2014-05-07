/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp.game;

import hu.elte.komp.model.Game;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Zsolt
 */
public abstract class AbstractGame implements GameInterface, GameGraphInterface {

    @Override
    public void doAiStep() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        
    /**
     * Visszaadja az adott nevhez tartozo pontszamito osztalyt.
     * @param name
     * @return 
     */
    protected abstract ScoreCalculator getScoreCalculator(String name);
    
    
    /**
     * Megmondja, mekkora az adott erteke, ha a jatekos AI. Ha human, 0-val ter vissza.
     * @param step
     * @return 
     */
    @Override
    public long getStepScoreForCurrentPlayer(Object step) {
        // TODO
        return 0;
    }

    @Override
    public Game getEntityInfo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
