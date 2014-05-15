/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp.kompgame.game.kamisado;

import hu.elte.komp.game.ScoreCalculator;

public class ZeroCalculator implements ScoreCalculator {

    @Override
    public long getScoreForStep(Object step) {
        return 0;
    }
    
}
