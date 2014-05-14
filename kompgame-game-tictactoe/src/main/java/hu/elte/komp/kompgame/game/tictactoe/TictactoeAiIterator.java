/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp.kompgame.game.tictactoe;

import java.util.Iterator;

/**
 *
 * @author Zsolt
 */
public class TictactoeAiIterator implements Iterator<TictactoeAiIterator.StepInfo> {

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
    
    int size = BoardHelper.n;
    
    int x, y; //  
    int direction, position; // target offset
    int wx, wy; // target position
    
    public TictactoeAiIterator(String s, boolean playerOne) {
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
        final char s = playerOne ? '1' : '2';
        final char e = playerOne ? '1' : '2';
        final char c = pieceAt(x, y);
        
        return c >= s && c <= e;
    }
    
    char pieceAt(int x, int y) {
        int pos = playerOne? y*size+x : (size*size)-1-(y*size+x);
        if (pos >= size*size || pos < 0) {
            throw new RuntimeException("Illegal position! x:"+x + " y:"+y);
        }
        return b.charAt(pos);
    }
    
    @Override
    public boolean hasNext() {
        // search next item
        while(y < size) {
            while (x < size) {
                if (isMyPiece(x, y)) {
                    while (direction < 3) {
                        position++;
                        switch (direction) {
                            case 0: wx = x - position; wy = y - position; break;
                            case 1: wx = x; wy = y - position; break;
                            case 2: wx = x + position; wy = y - position; break;
                        }
                        if (wx >= 0 && wx < size && wy >= 0 && wy < size && pieceAt(wx, wy) == ' ') {
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
        newBoard.replace(0, size*size, b);
        if (playerOne) {
            newBoard.replace(wy*size+wx, wy*size+wx+1, Character.toString(b.charAt(y*size+x)));
            newBoard.replace(y*size+x, y*size+x+1, " ");
        } else {
            newBoard.replace((size*size)-1-(wy*size+wx), (size*size)-1-(wy*size+wx)+1, Character.toString(b.charAt((size*size)-1-(y*size+x))));
            newBoard.replace((size*size)-1-(y*size+x), (size*size)-1-(y*size+x)+1, " ");
        }
        return new StepInfo(newBoard.toString(), !playerOne);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
