/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp.kompgame.game.kamisado;

import hu.elte.komp.game.Board;
import hu.elte.komp.game.SimplePiece;

/**
 *
 * A string reprezentacio: 8x8 karakter, a feher babuk abcdefgh, a sotet babuk
 * ijklmnop. Az aktivan kivalasztott babu nagybetuvel van irva.
 * 
 * @author Zsolt
 */
public class BoardHelper {
   
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
        switch (c) {
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

    static Board getBoardForString(String s) {
        Board b = new Board(8,8);
        
        for (int iy=0;iy<8;iy++) {
            for (int ix=0;ix<8;ix++) {
                SimplePiece sp = new SimplePiece();
                char c = s.charAt(iy*8+ix);
                
                sp.setBackgroundColor(colorToString(colorMap[iy][ix]));
                
                if (c != ' ') {
                    sp.setIsClickable(true);
                    sp.setContent("◉");
                    sp.setTextColor(colorToString(charToColor(c)));
                }
                
                b.getPieces()[iy][ix] = sp;
            }
        }
        
        return b;
    }
}
