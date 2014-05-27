/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp.kompgame.game.kamisado;

import hu.elte.komp.game.Board;
import java.util.Iterator;
import sun.security.action.GetBooleanAction;

public class KamisadoAiIterator implements Iterator<KamisadoAiIterator.StepInfo> {

    public static class StepInfo {
        String board;
        boolean nextIsPlayerOne;

        public StepInfo(String board, boolean nextIsPlayerOne) {
            this.board = board;
            this.nextIsPlayerOne = nextIsPlayerOne;
        }

        public String getBoard() {
            return board;
        }

        public boolean isNextIsPlayerOne() {
            return nextIsPlayerOne;
        }
    }
    
    String b;
    StringBuilder newBoard;
    boolean playerOne;
    
    int x, y; // tower position
    int direction, position; // target offset
    int wx, wy; // target position
    
    public KamisadoAiIterator(String s, boolean playerOne) {
        this.b = s;
        this.playerOne = playerOne;
        this.x  =0;
        this.y = 0;
        this.direction = 0;
        this.position = 0;
        this.wx = -1;
        this.wy = -1;
        newBoard = new StringBuilder(s);
    }

    private boolean isMyPiece(int x, int y) {
        final char s = playerOne ? 'a' : 'i';
        final char e = playerOne ? 'h' : 'p';
        final char c = pieceAt(x, y);
        
        return c >= s && c <= e;
    }
    
    char pieceAt(int x, int y) {
        int pos = playerOne? y*8+x : 63-(y*8+x);
        if (pos >= 64 || pos < 0) {
            throw new RuntimeException("Illegal position! x:"+x + " y:"+y);
        }
        return b.charAt(pos);
    }
    
    @Override
    public boolean hasNext() {
        
        char next = BoardHelper.nextMoveCharacter(b, playerOne);
        
        //nyertes állásokban nincs több lépés - teszt
        for (int i=0;i<8;i++) {
            if (b.charAt(i) >= 'a' && b.charAt(i) <= 'h') {
                return false;
            }
            if (b.charAt(63-i) >= 'i' && b.charAt(63-i) <= 'p') {
                return false;
            }
        }
 
        // search next item
        while(y < 8) {
            while (x < 8) {
                if (isMyPiece(x, y) && (next == ' ' || pieceAt(x,y) == next)) {
                    while (direction < 3) {
                        position++;
                        switch (direction) {
                            case 0: wx = x - position; wy = y - position; break;
                            case 1: wx = x; wy = y - position; break;
                            case 2: wx = x + position; wy = y - position; break;
                        }
                        if (wx >= 0 && wx < 8 && wy >= 0 && wy < 8 && pieceAt(wx, wy) == ' ') {
                            return true;
                        } else {
                            position = 0;
                            direction ++;
                        }
                    }
                }
                direction = 0;
                position = 0;
                x++;
            }
            y++;
            x = 0;
        }
        
        wx = 56666;
        wy = 66666;
        return false;
    }

    @Override
    public StepInfo next() {
        newBoard.replace(0, 64, b);
        if (newBoard.length() > 65) {
            newBoard.replace(64, newBoard.length(), "");
        }
        if (playerOne) {
            newBoard.replace(wy*8+wx, wy*8+wx+1, Character.toString(b.charAt(y*8+x)));
            newBoard.replace(64, 65, Character.toString(b.charAt(y*8+x))); // last moved char
            newBoard.replace(y*8+x, y*8+x+1, " ");
            
        } else {
            newBoard.replace(63-(wy*8+wx), 63-(wy*8+wx)+1, Character.toString(b.charAt(63-(y*8+x))));
            newBoard.replace(64, 65, Character.toString(b.charAt(63-(y*8+x)))); // last moved char
            newBoard.replace(63-(y*8+x), 63-(y*8+x)+1, " ");
        }
        return new StepInfo(newBoard.toString(), !playerOne);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
