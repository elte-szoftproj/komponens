/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp.kompgame.game.kamisado;

import hu.elte.komp.game.ScoreCalculator;

/**
 * Heurisztika:: nyertes állás: 100 pont, vesztes: -100, egyébként:0.
 * {@link hu.elte.komp.game.ScoreCalculator ScoreCalculator} interface.
 * @author angelid
 */
public class WinCalculator implements ScoreCalculator {

    @Override
    public long getScoreForStep(Object step) {
        KamisadoAiIterator.StepInfo si=(KamisadoAiIterator.StepInfo) step;
        
        //konkrét nyertes-vesztes állások értéke 100 0 -100
        int sum=calculateWin(si.board);
        
        return (long)sum;
    }
    
    private int calculateWin(String board) throws IndexOutOfBoundsException {
        for (int i=0;i<8;i++) {
            //nyertes állás, 100
            if (board.charAt(63-i) >= 'i' && board.charAt(63-i) <= 'p') {
                    return 100;
            }
            //vesztes állás, -100
            if (board.charAt(i) >= 'a' && board.charAt(i) <= 'h') {
                return -100;
            }            
        }
        return 0;   
    }
    
}
