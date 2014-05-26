/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp.kompgame.web2;

import hu.elte.komp.GameService;
import hu.elte.komp.game.GameInterface;
import hu.elte.komp.model.Game;
import hu.elte.komp.model.GameState;
import java.io.Serializable;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class GameSession implements Serializable {
        
    public Game getGame() {
        //return game;
        return gameInterface.getEntityInfo();
    }
    
    public GameInterface getGameInterface() {
        return gameInterface;
    }
    
    public String getPlayer() {
        return firstPlayer ? getGame().getPlayer1() : getGame().getPlayer2();
    }
    
    public boolean isFirstPlayer() {
        return firstPlayer;
    }
    
    public boolean isSecondPlayer() {
        return !firstPlayer;
    }
        
    public void newGame(Game game, GameInterface gameInterface, String player) {
        this.game = game;
        this.gameInterface = gameInterface;
        gameInterface.loadGameId(game.getId());
        this.game.setPlayer1("hu:" + player);
        this.game.setGameState(GameState.WAITING);
        this.firstPlayer = true;
        gameService.updateGame(game);
    }
    
    public void joinGame(Game game, GameInterface gameInterface, String player) {
        this.game = game;
        this.gameInterface = gameInterface;
        gameInterface.loadGameId(game.getId());
        this.game.setPlayer2("hu:" + player);
        this.game.setGameState(GameState.ONGOING_PLAYER1);
        this.firstPlayer = false;
        gameService.updateGame(game);
    }
    
    //@PreDestroy
    public void leaveGame() {
        if (game.getGameState() == GameState.ONGOING_PLAYER1 || game.getGameState() == GameState.ONGOING_PLAYER2) {
            if (firstPlayer) {
                game.setGameState(GameState.PLAYER2_WON);
            } else {
                game.setGameState(GameState.PLAYER1_WON);
            }
            em.merge(game);
        } else {
            try {
                em.remove(game);
            } catch (RuntimeException ex) {
                FacesContext.getCurrentInstance().addMessage(ex.getMessage(), null);
            }
        }
    }    
    
    private Game game;
    private GameInterface gameInterface;
    private boolean firstPlayer;
    
    
    @EJB
    private GameService gameService;
    @EJB
    private TransactionalEntityManager em;
}
