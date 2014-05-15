/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp.kompgame.game.test.tictactoe;

import hu.elte.komp.game.ScoreCalculator;

public class WinnerScoringCalculator implements ScoreCalculator {

    private BoardHelper boardHelper;
    
    @Override
    public long getScoreForStep(Object step) {
        String board = (String) step;
        if (boardHelper.isWinningState(board)) {
            //FIXME
        }
        return 0;
    }
    
}
