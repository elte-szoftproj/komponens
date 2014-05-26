/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp.kompgame.game.tictactoe;


import hu.elte.komp.game.Board;
import hu.elte.komp.game.Position;
import hu.elte.komp.game.SimplePiece;
import hu.elte.komp.model.Game;
import hu.elte.komp.model.GameState;
import java.util.Iterator;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author user
 */
public class BoardHelper {
    
    /**
     * Helper class for maintaining the gameboard
     */
    public static int n=5; //egyenlőre a méret beégetve
    //private static int[][] board = new int[n][n];

    static GameState getWinningState(String board) {
        TictactoeBoard tb = new TictactoeBoard(board);
        
        if (tb.getScore1() >= 5)      {
                return GameState.PLAYER1_WON;
        }
         
        if (tb.getScore2() >= 5)      {
                return GameState.PLAYER2_WON;
        }
        
        return null;
    }
    
    enum Color {
        BLUE, RED
    }
     
    
    static String colorToString(Color color) {
        switch(color) {
            case RED: return "red";
            case BLUE: return "blue";
            }
        
        return "";
    }
    
    static String getInitialBoard() {    
        return "2:2:0000";
    }
    
    static class MoveResult { 
        String str;
        
        GameState nextState;

        public MoveResult(String str, GameState nextState) {
            this.str = str;
            this.nextState = nextState;
        }
    }
    
    static MoveResult clickOn(String s, Position pos, boolean isPlayerOne) {
       
        TictactoeBoard tb = new TictactoeBoard(s);
        
        try {
            TictactoeBoard clickedBoard = new TictactoeBoard(tb, pos.getX(), pos.getY(), isPlayerOne? 1 : 2);
            
            if (clickedBoard.score1 >= 5) {
                return new MoveResult(clickedBoard.toString(), GameState.PLAYER1_WON);
            }
            if (clickedBoard.score2 >= 5) {
                return new MoveResult(clickedBoard.toString(), GameState.PLAYER2_WON);
            }
            
            return new MoveResult(clickedBoard.toString(), isPlayerOne ? GameState.ONGOING_PLAYER2 : GameState.ONGOING_PLAYER1);
        } catch (Exception e) {
            return new MoveResult(s, null);
        }        
    }

    static Board getBoardForString(String s, boolean isPlayerOne) {
        
        TictactoeBoard tb = new TictactoeBoard(s);
        
        Board b = new Board(tb.w+2,tb.h+2);
        
        for (int iy=0;iy<tb.h+2;iy++) {
            for (int ix=0;ix<tb.w+2;ix++) {
                SimplePiece sp = new SimplePiece();
                
                sp.setBackgroundColor("YELLOW");
 
                if (iy < 1 || ix < 1 || iy == tb.h + 1 || ix == tb.w + 1) {
                    sp.setContent(""); sp.setIsClickable(true);
                } else {
                    switch (tb.board[iy-1][ix-1]) {
                        case 1: sp.setContent("X"); break;
                        case 2: sp.setContent("O"); break;
                        case 0: sp.setContent(""); sp.setIsClickable(true); break;
                    }
                }
                                                
                b.getPieces()[iy][ix] = sp;
            }
        }
        
        return b;
    }
    
    static Iterator<TictactoeAiIterator.StepInfo> getPossibleSteps(String s, boolean isPlayerOne) {
        return new TictactoeAiIterator(s, isPlayerOne);
    }
    
    
}
