/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp.game;

/**
 *
 * Reprezentalja a jatektablat.
 * 
 * A tabla egy negyzetracsbol all, amiknek a megjeleneset CSS-el lehet szabalyozni, 
 * valamint mindegyikrol el lehet donteni, hogy klikkelheto e. Pl. igy lehet megoldani
 * a sakktabla szeleit, ahol csak feliratok vannak.
 * 
 * @author Zsolt
 */
public class Board {
    
    private Piece[][] pieces;

    public Board(int w, int h) {
        pieces = new Piece[h][w];
    }

    
    public Piece[][] getPieces() {
        return pieces;
    }
    
    Piece getAt(Position pos) {
        return pieces[pos.getY()][pos.getX()];
    }
    
    public int getWidth() {
        return pieces[0].length;
    }
    
    public int getHeight() {
        return pieces.length;
    }
        
}
