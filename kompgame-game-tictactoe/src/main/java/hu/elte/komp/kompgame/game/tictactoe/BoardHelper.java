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
    
    public static int n=5; //egyenlőre a méret beégetve
    //private static int[][] board = new int[n][n];

    static GameState getWinningState(String board) {
        // TODO: patt implementalasa
        
        if ((checkLines(board) == 1) || (checkLines(board) == 1) ||
             checkDiagonals(board) == 1)      {
                return GameState.PLAYER1_WON;
        }
         
        else if ((checkLines(board) == -1) || (checkLines(board) == -1) ||
             checkDiagonals(board) == -1)      {
                return GameState.PLAYER2_WON;
        }
        //        return GameState.STALEMATE;
              
        
        return null;
    }
    
    
     static int checkLines(String board){ 
        //haználni a nyerés kiszámoláshoz az int tömböt is és
        //és az eredményt visszarakni a string-láncba
        int szum;
        String[] temp = splitStringEvery(board, n);
        
        for(int line=0 ; line<n ; line++){
            
            szum = 0;
            
            for (int i = 0; i < n; i++) {
                
                if (temp[line].charAt(i) == '1')
                szum++ ;
                
                else if (temp[line].charAt(i) == '2')
                szum-- ;
            }
            
            if( szum == -n) 
              return -1; 
            else if( szum == n) 
              return 1;
        }
          
        return 0; 
    }
    
    static int checkColumns(String board){ 
        
        int szum;
        String[] temp = splitStringEvery(board, n);
        
        for(int column=0 ; column<n ; column++){ 
            
            szum = 0;
            
            for (int i = 0; i < n; i++) {
                
                if (temp[i].charAt(column) == '1')
                szum++ ;
                
                else if (temp[i].charAt(column) == '2')
                szum-- ;
                
            }
            
            if( szum == -n) 
              return -1; 
            else if( szum == n) 
              return 1;
        }      
        
        return 0; 
    } 
    
   
    static int checkDiagonals(String board){
        //haználni a nyerés kiszámoláshoz az int tömböt is és
        //és az eredményt visszarakni a string-láncba
        int szum  = 0;
        int szum2 = 0;
        String[] temp = splitStringEvery(board, n);
        
        for(int i=0 ; i<n ; i++){ 
                     
            if (temp[i].charAt(i) == '1')
                szum++ ;
                
            else if (temp[i].charAt(i) == '2')
                szum-- ;
                     
        }
        
        for(int i=0 ; i<n ; i++){ 
                     
            if (temp[i].charAt(i) == '1')
                szum++ ;
                
            else if (temp[i].charAt(i) == '2')
                szum-- ;
                     
        }
        
        if( szum == -n) 
          return -1; 
        else if( szum == n) 
          return 1;
        else if( szum2 == -n) 
          return -1; 
        else if( szum2 == n) 
          return 1;

        return 0; 
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
    
     
    // TODO: megforditani a boardot a sotetnek?
    static String getInitialBoard() {
        
        String temp = "";
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                
                temp = temp + "0";
            }
        }
        
        return temp;        
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

        int chpos = isPlayerOne? pos.getY()*n+pos.getX() : ((n-1)-pos.getY())*n+pos.getX();
        int cy = pos.getY();
        int cx = pos.getX();
        
        char c = s.charAt(chpos);
        
        Position largePos = new Position(-1,-1);
        Board b = getBoardForString(s, isPlayerOne, largePos);
                
        SimplePiece p = (SimplePiece) b.getPieces()[cy][cx];
        
        if (!p.isClickable()) {
            return new MoveResult(s, false, null);
        }
        
        if (c == '0') {
            // move upcase there as downcase
            int lpos = isPlayerOne? largePos.getY()*n+largePos.getX() : ((n-1)-largePos.getY())*n+largePos.getX();
            char l = s.charAt(lpos);
            s = s.replace(l, '0');
            StringBuilder sb = new StringBuilder(s);
            sb.setCharAt(chpos, Character.toLowerCase(l));
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
        Board b = new Board(n,n);
        
        
        if (!isPlayerOne) {
            // reverse the lines, upward direction is to the top!
            String[] reva = splitStringEvery(s, n);
            ArrayUtils.reverse(reva);
            s = StringUtils.join(reva, "");
        }
                
        for (int iy=0;iy<n;iy++) {
            for (int ix=0;ix<n;ix++) {
                SimplePiece sp = new SimplePiece();
                char c = s.charAt(iy*n+ix);
                
                sp.setBackgroundColor("YELLOW");
                
                if (c != '0') {
                    if (c == '1' )  {
                        sp.setContent("◉");
                        if (!Character.isUpperCase(c) && isPlayerOne) { sp.setIsClickable(true); sp.setTextColor(colorToString(Color.BLUE)); }
                    } else if (c == '2' ) {
                        sp.setContent("◈");
                        if (!Character.isUpperCase(c) && !isPlayerOne) { sp.setIsClickable(true); sp.setTextColor(colorToString(Color.RED)); }
                    }
                    
                } else {
                    sp.setContent("");
                }
                
                if (Character.isUpperCase(c) && (c >= 'I' ^ isPlayerOne)) {
                    active.setX(ix);
                    active.setY(iy);
                    sp.setStyleOverride("border-color: #f00;");
                }
                
                b.getPieces()[iy][ix] = sp;
            }
        }
        
        if (active.getX() != -1) {
            
            char act = s.charAt(active.getY()*n+active.getY());
            if (isPlayerOne || act >= 'I') {
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
                    if (active.getX() + d <= (n-1) && ! stopRight) {
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
        }     
        
        return b;
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
    
    static Iterator<TictactoeAiIterator.StepInfo> getPossibleSteps(String s, boolean isPlayerOne) {
        return new TictactoeAiIterator(s, isPlayerOne);
    }
    
    
}
