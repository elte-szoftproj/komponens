/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp.kompgame.game.kamisado;

import hu.elte.komp.game.ScoreCalculator;

/**
 * Heurisztika: az egyes állásokat aszerint értékeli, hogy hány
 * nyertes és hány vesztes lépés van belőle.
 * {@link hu.elte.komp.game.ScoreCalculator ScoreCalculator} interface.
 * @author angelid
 */
public class StrikeCalculator implements ScoreCalculator {

    @Override
    public long getScoreForStep(Object step) {
        KamisadoAiIterator.StepInfo si=(KamisadoAiIterator.StepInfo) step;        
        int sum=0;
        
        //lehetséges nyertes lépések
        for (char ch: "ijklmnop".toCharArray()) {            
            int x=si.board.indexOf(ch) % 8;
            int y=si.board.indexOf(ch) / 8;
            if (si.board.charAt(56+x)==' ') ++sum;
            if ((x+(7-y)<8) && (si.board.charAt(56+x+(7-y))==' ')) ++sum;
            if ((x-(7-y)>=0) && (si.board.charAt(56+x-(7-y))==' ')) ++sum;
        }
//        
//        //lehetséges vesztes lépések - az ellenfél részéről
//        for (char ch: "abcdefgh".toCharArray()) {            
//            int x=si.board.indexOf(ch) % 8;
//            int y=si.board.indexOf(ch) / 8;
//            if (si.board.charAt(x)==' ') --sum;
//            if ((x+y<8) && (si.board.charAt(x-y)==' ')) --sum;
//            if ((x-y>=0) && (si.board.charAt(x-y)==' ')) --sum;
//        }
        
        return sum;
    }
    
}
