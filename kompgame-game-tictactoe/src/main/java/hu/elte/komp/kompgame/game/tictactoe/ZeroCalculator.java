/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp.kompgame.game.tictactoe;

import hu.elte.komp.game.ScoreCalculator;

/**
 *
 * @author user
 */
public class ZeroCalculator implements ScoreCalculator {

    /**
     *
     * @param step
     * @return
     */
    @Override
    public long getScoreForStep(Object step) {
        return 0;
    }
    
}
