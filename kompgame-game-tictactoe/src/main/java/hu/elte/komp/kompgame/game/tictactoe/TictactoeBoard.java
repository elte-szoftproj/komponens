/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp.kompgame.game.tictactoe;

public class TictactoeBoard {
   
    
    int w;
    int h;
    int[][] board; // first y/h, second w/x
    
    int score1;
    int score2;
    
    public TictactoeBoard(String s) {
        String[] a = s.split(":");
        w = Integer.parseInt(a[0]);
        h = Integer.parseInt(a[1]);
        board = new int[h][w];
        for(int iy=0;iy<h;iy++) {
            for(int ix=0;ix<w;ix++) {
                board[iy][ix] = Integer.parseInt(a[2].charAt(iy*w+ix)+"");
            }
        }
        
        calculateScores();
    }
    
    /**
     * Creates a new board from a click.
     * 0 = before first item
     * 1 = first item
     * n = last item
     * n+1 = after last item
     * @param base
     * @param x
     * @param y
     * @param v 
     */
    public TictactoeBoard(TictactoeBoard base, int x, int y, int v) {
        w = base.w;
        h = base.h;
        int dx = 0; int rx = 0;
        int dy = 0; int ry = 0;
        if (x == 0) {
            w++;
            dx = 1;
        }
        if (x == w+1) {
            w++;
            rx++;
        }
        if (y == 0) {
            h++;
            dy = 1;
        }
        if (y == h+1) {
            h++;
            ry++;
        }
        if (dy != 1) y--;
        if (dx != 1) x--;
        board = new int[h][w];
        for(int iy=0;iy<h-dy-ry;iy++) {
            for(int ix=0;ix<w-dx-rx;ix++) {
                board[iy+dy][ix+dx] = base.board[iy][ix];
            }
        }
        if (board[y][x] != 0) {
            throw new RuntimeException("Illegal move!");
        }
        board[y][x] = v;
        
        calculateScores();
    }

    public int getScore1() {
        return score1;
    }

    public int getScore2() {
        return score2;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        
        ret.append(w).append(":");
        ret.append(h).append(":");
        for(int iy=0;iy<h;iy++) {
            for(int ix=0;ix<w;ix++) {
                ret.append(board[iy][ix]);
            }
        }
        
        return ret.toString();
    }

    private void calculateScores() {
        score1 = 0; score2 = 0;
        for(int iy=0;iy<h;iy++) {
            for(int ix=0;ix<w;ix++) {
                if (board[iy][ix] == 1) {
                    int tmp = calculateScoreAt(ix, iy);
                    if (tmp > score1) {
                        score1 = tmp;
                    }
                }
                if (board[iy][ix] == 2) {
                    int tmp = calculateScoreAt(ix, iy);
                    if (tmp > score2) {
                        score2 = tmp;
                    }
                }
            }
        }
    }

    private int calculateScoreAt(int ix, int iy) {
        
        int d1 = 1;
        while(ix+d1 < w && board[iy][ix+d1] == board[iy][ix]) { d1++; }
        
        int d2 = 1;
        while(iy+d2 < h && board[iy+d2][ix] == board[iy][ix]) { d2++; }
        
        int d3 = 1;
        while(iy+d3 < h && ix+d3 < w && board[iy+d3][ix+d3] == board[iy][ix]) { d3++; }
        
        return Math.max(Math.max(d1, d2), d3);
    }
    
    
}
