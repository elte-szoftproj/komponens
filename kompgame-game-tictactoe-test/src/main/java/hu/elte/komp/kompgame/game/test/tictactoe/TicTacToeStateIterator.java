package hu.elte.komp.kompgame.game.test.tictactoe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TicTacToeStateIterator implements Iterator<String> {

    private String board;
    private List<Integer> possiblePlaces;
    private Iterator<Integer> placeIterator;
    
    TicTacToeStateIterator(String board) {
        this.board = board;
        this.possiblePlaces = new ArrayList<>();
        
        for (int i = 0; i < board.length(); ++i) {
            if (board.charAt(i) == ' ') {
                possiblePlaces.add(i);
            }
        }
        
        placeIterator = possiblePlaces.iterator();
    }
    
    @Override
    public boolean hasNext() {
        return placeIterator.hasNext();
    }

    @Override
    public String next() {
        StringBuilder boardBuilder = new StringBuilder(board);
        // if both players have equal symbols, it's player1's move, else 
        // player2's
        boolean hasEqualSymbols = 
                (countOccurrences(board, TicTacToe.FIRST_SYMBOL) -
                 countOccurrences(board, TicTacToe.SECOND_SYMBOL)) == 0;
        char playerSymbol = hasEqualSymbols ? TicTacToe.FIRST_SYMBOL : 
                TicTacToe.SECOND_SYMBOL;
        boardBuilder.setCharAt(placeIterator.next(), playerSymbol);
        return boardBuilder.toString();
    }
    
    private int countOccurrences(String s, char c) {
        return s.length() - s.replaceAll(String.valueOf(c), "").length();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
