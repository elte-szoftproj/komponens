package hu.elte.komp.kompgame.game.test.tictactoe;

import hu.elte.komp.game.Board;
import hu.elte.komp.game.SimplePiece;

public class BoardHelper {
    
    public Board getBoardFromString(String strBoard, boolean needsFlip) {
        Board board = new Board(3, 3);
        
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                SimplePiece simplePiece = new SimplePiece();
//                char c = strBoard.charAt(i*3 + j);
//                if (c == 'X') {
//                    simplePiece.setContent("X");
//                } else if (c == 'O') {
//                    simplePiece.setContent("O");
//                } else {
//                    simplePiece.setContent(" ");
//                }
                simplePiece.setContent(
                        String.valueOf(strBoard.charAt(i * 3 + j)));
                if (strBoard.charAt(i * 3 + j) == ' ') {
                    simplePiece.setIsClickable(true);
                }
                board.getPieces()[2-j][2-i] = simplePiece;
            }
        }
        
        if (true) {
            Board tmp = board;
            board = new Board(3, 3);
            
            for (int i = 0; i < 3; ++i) {
                for (int j = 0; j < 3; ++j) {
                    board.getPieces()[i][j] = tmp.getPieces()[2 - i][2 - j];
                }
            }
        }
        
        return board;
    }
    
    public boolean isWinningState(String board) {
        
        for (int i = 0; i < 3; ++i) {
            // rows
            if (board.charAt(i * 3) != ' ' && 
                    board.charAt(i*3) == board.charAt(i*3+1) && 
                    board.charAt(i*3) == board.charAt(i*3+2)) {
                return true;
            }
            // columns
            if (board.charAt(i) != ' ' && 
                    board.charAt(i) == board.charAt(i + 3) &&
                    board.charAt(i) == board.charAt(i + 6)) {
                return true;
            }
        }
        // first diagonal
        if (board.charAt(0) != ' ' && 
                board.charAt(0) == board.charAt(4) && 
                board.charAt(0) == board.charAt(8)) {
            return true;
        }
        // second diagonal
        if (board.charAt(2) != ' ' && 
                board.charAt(2) == board.charAt(4) &&
                board.charAt(2) == board.charAt(6)) {
            return true;
        }
        return false;
    }
    
    public boolean isSlalemate(String board) {
        return (!board.contains(" ")) ;
    }
    
    public String getInitialBoard() {
        // 9 spaces
        return "         ";
    }
    
}
