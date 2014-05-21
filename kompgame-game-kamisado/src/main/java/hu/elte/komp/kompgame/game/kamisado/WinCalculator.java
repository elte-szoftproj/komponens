/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp.kompgame.game.kamisado;

import hu.elte.komp.game.ScoreCalculator;

/**
 * Heurisztika:: nyertes állás: 100 pont, vesztes: 0, egyébként:50.
 * {@link hu.elte.komp.game.ScoreCalculator ScoreCalculator} interface.
 * @author angelid
 */
public class WinCalculator implements ScoreCalculator {

    @Override
    public long getScoreForStep(Object step) {
        KamisadoAiIterator.StepInfo si=(KamisadoAiIterator.StepInfo) step;
        for (int i=0;i<8;i++) {
            //nyertes állás, 100
            if (si.board.charAt(63-i) >= 'i' && si.board.charAt(63-i) <= 'p') {
                    return 100;
            }
            //vesztes állás, 0
            if (si.board.charAt(i) >= 'a' && si.board.charAt(i) <= 'h') {
                return 0;
            }            
        }
        return 50;
    }
    
}
