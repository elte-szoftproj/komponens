/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp.kompgame.web2;

import hu.elte.komp.game.Piece;
import hu.elte.komp.game.Position;
import hu.elte.komp.model.GameState;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class PlayGameScreen {
    
    public boolean isGameOpen() {
        return gameSession.getGame() != null;
    }
    
    public boolean isWaiting() {
        return gameSession.getGame().getGameState() == GameState.WAITING;
    }
    
    public boolean isEnded() {
        return gameSession.getGame().getGameState() == GameState.PLAYER1_WON
                || gameSession.getGame().getGameState() == GameState.PLAYER2_WON
                || gameSession.getGame().getGameState() == GameState.STALEMATE;
    }
    
    public boolean havePlayerWon() {
        return (gameSession.isFirstPlayer() && gameSession.getGame().getGameState() == GameState.PLAYER1_WON)
                || (gameSession.isSecondPlayer() && gameSession.getGame().getGameState() == GameState.PLAYER2_WON);
    }
    
    public boolean havePlayerLost() {
        return (gameSession.isFirstPlayer() && gameSession.getGame().getGameState() == GameState.PLAYER2_WON)
                || (gameSession.isSecondPlayer() && gameSession.getGame().getGameState() == GameState.PLAYER1_WON);
    }
    
    public boolean isStalemate() {
        return gameSession.getGame().getGameState() == GameState.STALEMATE;
    }
    
    public String getGameName() {
        return (gameSession.getGameInterface() != null) ? gameSession.getGameInterface().getGameTypeName() : "";
    }
    
    public boolean isPlayerActive() {
        return (gameSession.isFirstPlayer() && gameSession.getGame().getGameState() == GameState.ONGOING_PLAYER1)
                || (gameSession.isSecondPlayer() && gameSession.getGame().getGameState() == GameState.ONGOING_PLAYER2);
    }

    public boolean isPlayerPassive() {
        return (gameSession.isFirstPlayer() && gameSession.getGame().getGameState() == GameState.ONGOING_PLAYER2)
                || (gameSession.isSecondPlayer() && gameSession.getGame().getGameState() == GameState.ONGOING_PLAYER1);
    }
    
    public int getGameTableWidth() {
        return gameSession.getGameInterface().getCurrentBoard(gameSession.getPlayer()).getWidth();    
    }

    public int getGameTableHeight() {
        return gameSession.getGameInterface().getCurrentBoard(gameSession.getPlayer()).getHeight();    
    }

    public Piece[][] getGameTable() {
        return gameSession.getGameInterface().getCurrentBoard(gameSession.getPlayer()).getPieces();
    }
    
    public String onCellClick(Piece piece, int x, int y) {
        if (piece.isClickable() && isPlayerActive()) {
            gameSession.getGameInterface().clickedOn(gameSession.getPlayer(), new Position(x, y));
            
            while (gameSession.getGame().isCurrentPlayerAi()) {
                gameSession.getGameInterface().doAiStep();
            }
        }
        return null;
    }
    
    public String onPoll() {
        return null;
    }
            
    public GameSession getGameSession() {
        return gameSession;
    }
    
    public void setGameSession(GameSession value) {
        gameSession = value;
    }
    
    @ManagedProperty("#{gameSession}")
    private GameSession gameSession;
}
