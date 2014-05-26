/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp.kompgame.game.kamisado;

import hu.elte.komp.game.ScoreCalculator;

/**
 * Heurisztika: az egyes állásokat aszerint értékeli, hogy hány
 * nyertes és hány vesztes lépés van belőle. A konkrét nyertes és vesztes állások értéke 100 és -100.
 * {@link hu.elte.komp.game.ScoreCalculator ScoreCalculator} interface.
 * @author angelid
 */
public class StrikeCalculator implements ScoreCalculator {
    final static String AILETTERS="ijklmnop";
    final static String HUMANLETTERS="abcdefgh";

    @Override
    public long getScoreForStep(Object step) {
        KamisadoAiIterator.StepInfo si=(KamisadoAiIterator.StepInfo) step;
        
        //konkrét nyertes-vesztes állások értéke 100 0 -100
        int sum=calculateWin(si.board);
        
        //lehetséges nyertes lépések
        sum+=calculateWinStrikes(si.board,AILETTERS);
        
        //lehetséges vesztes lépések
        String newboard=new StringBuilder(si.board).reverse().toString();
        sum-=calculateWinStrikes(newboard,HUMANLETTERS);
        
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
    
    private int calculateWinStrikes(String board, String letters) throws IndexOutOfBoundsException {
        int strikes=0;
        for (char ch: letters.toCharArray()) {            
            int x=board.indexOf(ch) % 8;
            int y=board.indexOf(ch) / 8;
            int dx;
            boolean win;
            
            //egyenes lépés vizsgálata
            win=true;
            dx=x+8;
            while (dx<64 && win){
                win=(board.charAt(dx)==' ');
                dx+=8;
            }
            if (win) ++strikes;
            
            //egyik irány, ha létezik a lépés
            if ((x+(7-y)<8)){
                win=true;
                dx=x+9;
                while (dx<64 && win){
                    win=(board.charAt(dx)==' ');
                    dx+=9;
                }
                if (win) ++strikes;
            }
            
            //másik irány, ha létezik a lépés
            if ((x-(7-y)>=0)){
                win=true;
                dx=x+7;
                while (dx<64 && win){
                    win=(board.charAt(dx)==' ');
                    dx+=7;
                }
                if (win) ++strikes;
            }
        }
        return strikes;        
    }
    
}
