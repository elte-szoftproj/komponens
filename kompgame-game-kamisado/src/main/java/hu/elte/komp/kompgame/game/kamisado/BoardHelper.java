/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp.kompgame.game.kamisado;

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
 * A string reprezentacio: 8x8 karakter, a sotet (p1) babuk abcdefgh, a feher babuk (p2)
 * ijklmnop. Az aktivan kivalasztott babu nagybetuvel van irva.
 * 
 * Igazabol ez az osztaly tartalmazza a jatek kb. teljes logikajat is
 * 
 * @author Zsolt
 */
public class BoardHelper {

    static GameState getWinningState(String board) {
        // TODO: patt implementalasa
        
        for (int i=0;i<8;i++) {
            if (board.charAt(i) >= 'a' && board.charAt(i) <= 'h') {
                return GameState.PLAYER1_WON;
            }
            if (board.charAt(63-i) >= 'i' && board.charAt(63-i) <= 'p') {
                return GameState.PLAYER1_WON;
            }
        }
        
        return null;
    }
   
    enum Color {
        ORANGE, BLUE, PURPLE, PINK, YELLOW, RED, GREEN, BROWN
    }
    
    static final Color[][] colorMap =  {
        { Color.ORANGE, Color.BLUE, Color.PURPLE, Color.PINK, Color.YELLOW, Color.RED, Color.GREEN, Color.BROWN },
        { Color.RED, Color.ORANGE, Color.PINK, Color.GREEN, Color.BLUE, Color.YELLOW, Color.BROWN, Color.PURPLE },
        { Color.GREEN, Color.PINK, Color.ORANGE, Color.RED, Color.PURPLE, Color.BROWN, Color.YELLOW, Color.BLUE },
        { Color.PINK, Color.PURPLE, Color.BLUE, Color.ORANGE, Color.BROWN, Color.GREEN, Color.RED, Color.YELLOW },
        { Color.YELLOW, Color.RED, Color.GREEN, Color.BROWN, Color.ORANGE, Color.BLUE, Color.PURPLE, Color.PINK },
        { Color.BLUE, Color.YELLOW, Color.BROWN, Color.PURPLE, Color.RED, Color.ORANGE, Color.PINK, Color.GREEN },
        { Color.PURPLE, Color.BROWN, Color.YELLOW, Color.BLUE, Color.GREEN, Color.PINK, Color.ORANGE, Color.RED } ,
        { Color.BROWN, Color.GREEN, Color.RED, Color.YELLOW, Color.PINK, Color.PURPLE, Color.BLUE, Color.ORANGE }
    };
    
    static String colorToString(Color color) {
        switch(color) {
            case ORANGE: return "orange";
            case RED: return "red";
            case GREEN: return "green";
            case PINK: return "pink";
            case YELLOW: return "yellow";
            case BLUE: return "blue";
            case PURPLE: return "purple";
            case BROWN: return "brown";
        }
        
        return "";
    }
    
    static Color charToColor(char c) {
        switch (Character.toLowerCase(c)) {
            case 'i':
            case'h': 
                return Color.ORANGE;
            case 'j':
            case 'g': 
                return Color.BLUE;
            case 'k':
            case 'f':
                return Color.PURPLE;
            case 'l': 
            case 'e':
                return Color.PINK;
            case 'm':
            case 'd':
                return Color.YELLOW;
            case 'n':
            case 'c':
                return Color.RED;
            case 'o':
            case 'b':
                return Color.GREEN;
            case 'p':
            case 'a':
                return Color.BROWN;
        }
        
        return null;
    }
    
    // TODO: megforditani a boardot a sotetnek?
    static String getInitialBoard() {
        return 
                "ijklmnop" +
                "        " +
                "        " +
                "        " +
                "        " +
                "        " +
                "        " +
                "abcdefgh"
                ;
    }
    
    static class MoveResult { 
        String str;
        
        boolean endTurn;
        
        GameState nextState;

        public MoveResult(String str, boolean endTurn, GameState nextState) {
            this.str = str;
            this.endTurn = endTurn;
            this.nextState = nextState;
        }
    }
    
    static MoveResult clickOn(String s, Position pos, boolean isPlayerOne) {
        if (!isPlayerOne) {
            // reverse the lines, upward direction is to the top!
            String[] reva = splitStringEvery(s, 8);
            ArrayUtils.reverse(reva);
            s = StringUtils.join(reva, "");
        }
        
        char c = s.charAt(pos.getY()*8+pos.getX());
        
        Position largePos = new Position(-1,-1);
        Board b = getBoardForString(s, isPlayerOne, largePos);
                
        SimplePiece p = (SimplePiece) b.getPieces()[pos.getY()][pos.getX()];
        
        if (!p.isClickable()) {
            return new MoveResult(s, false, null);
        }
        
        if (c == ' ') {
            // move upcase there as downcase
            char l = s.charAt(largePos.getY()*8+largePos.getX());
            s = s.replace(l, ' ');
            StringBuilder sb = new StringBuilder(s);
            sb.setCharAt(pos.getY()*8+pos.getX(), Character.toLowerCase(l));
            // TODO: check end condition
            GameState gs = getWinningState(sb.toString());
            if (gs != null) {
                return new MoveResult(sb.toString(), true, gs);
            }
            return new MoveResult(sb.toString(), true, isPlayerOne ? GameState.ONGOING_PLAYER2 : GameState.ONGOING_PLAYER1);
        } else {
            return new MoveResult(s.toLowerCase().replace(c, Character.toUpperCase(c)), false, null);
        }
        
    }

    static Board getBoardForString(String s, boolean isPlayerOne) {
        return getBoardForString(s, isPlayerOne, new Position(-1,-1));
    }
    
    static Board getBoardForString(String s, boolean isPlayerOne, Position active) {
        Board b = new Board(8,8);
        
        
        if (!isPlayerOne) {
            // reverse the lines, upward direction is to the top!
            String[] reva = splitStringEvery(s, 8);
            ArrayUtils.reverse(reva);
            s = StringUtils.join(reva, "");
        }
                
        for (int iy=0;iy<8;iy++) {
            for (int ix=0;ix<8;ix++) {
                SimplePiece sp = new SimplePiece();
                char c = s.charAt(iy*8+ix);
                
                sp.setBackgroundColor(colorToString(colorMap[iy][ix]));
                
                if (c != ' ') {
                    if (c < 'i') {
                        sp.setContent("◉");
                        if (!Character.isUpperCase(c) && isPlayerOne) { sp.setIsClickable(true); }
                    } else {
                        sp.setContent("◈");
                        if (!Character.isUpperCase(c) && !isPlayerOne) { sp.setIsClickable(true); }
                    }
                    sp.setTextColor(colorToString(charToColor(c)));
                } else {
                    sp.setContent("");
                }
                
                if (Character.isUpperCase(c)) {
                    active.setX(ix);
                    active.setY(iy);
                    sp.setStyleOverride("border-color: #f00;");
                }
                
                b.getPieces()[iy][ix] = sp;
            }
        }
        
        if (active.getX() != -1) {
            int d = 0;
            boolean stopLeft = false, stopUp = false, stopRight = false;
            for (int i = active.getY() - 1 ; i >= 0 ; i--) {
                d++;
                if (active.getX() - d >= 0 && !stopLeft) {
                    SimplePiece p = (SimplePiece) b.getPieces()[i][active.getX() - d];
                    if (!p.getContent().equals("")) { stopLeft = true; } else {
                        p.setStyleOverride("border-color: cyan; ");
                        p.setIsClickable(true);
                    }
                }
                if (active.getX() + d <= 7 && ! stopRight) {
                    SimplePiece p = (SimplePiece) b.getPieces()[i][active.getX() + d];
                    if (!p.getContent().equals("")) { stopRight = true; } else {
                        p.setStyleOverride("border-color: cyan; ");
                        p.setIsClickable(true);
                    }
                }
                if (!stopUp) {
                    SimplePiece p = (SimplePiece) b.getPieces()[i][active.getX()];
                    if (!p.getContent().equals("")) { stopUp  = true; } else {
                        p.setStyleOverride("border-color: cyan; ");
                        p.setIsClickable(true);
                    }
                }
            }
        }
        
        
        return b;
    }


    static Iterator<KamisadoAiIterator.StepInfo> getPossibleSteps(String s, boolean isPlayerOne) {
        return new KamisadoAiIterator(s, isPlayerOne);
    }
    
    static String[] splitStringEvery(String s, int interval) {
        int arrayLength = (int) Math.ceil(((s.length() / (double) interval)));
        String[] result = new String[arrayLength];

        int j = 0;
        int lastIndex = result.length - 1;
        for (int i = 0; i < lastIndex; i++) {
            result[i] = s.substring(j, j + interval);
            j += interval;
        } //Add the last bit
        result[lastIndex] = s.substring(j);

        return result;
    }
}
