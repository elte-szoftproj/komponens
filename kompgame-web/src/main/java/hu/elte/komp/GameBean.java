/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp;

import hu.elte.komp.game.Board;
import hu.elte.komp.game.GameInterface;
import hu.elte.komp.game.Position;
import hu.elte.komp.model.GameState;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Zsolt
 */
@Named(value = "GameBean")
@RequestScoped
public class GameBean {

    private String gameId;
    
    private String clickR;
    private String clickC;
    
    GameInterface game;
    
    /**
     * Creates a new instance of komp
     */
    public GameBean() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        this.gameId = facesContext.getExternalContext().getRequestParameterMap().get("id");
        this.clickR = facesContext.getExternalContext().getRequestParameterMap().get("clickR");
        this.clickC = facesContext.getExternalContext().getRequestParameterMap().get("clickC");
        
        if (clickR != null) {
            int r = Integer.parseInt(clickR);
            int c = Integer.parseInt(clickC);
            Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
            getGame().clickedOn("hu:" + principal.getName(), new Position(c, r));
        }
        
        if (getGame().getEntityInfo().isCurrentPlayerAi()) {
            System.out.println("nop");
            getGame().doAiStep();
        }
        
        //getGame();
    }
    
    public boolean isOver() {
        GameState gs = getGame().getEntityInfo().getGameState();
        
        return (gs == GameState.PLAYER1_WON || gs == GameState.PLAYER2_WON || gs == GameState.STALEMATE);
    }
    
    public boolean waitingForSomebody() {
        Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        
        return !isOver() && !getGame().getEntityInfo().isCurrentPlayer(principal.getName());
    }
    
    public String getStepInfo() {
        GameState gs = getGame().getEntityInfo().getGameState();
        
        switch (gs) {
            case ONGOING_PLAYER1: return "Player 1 kovetkezik";
            case ONGOING_PLAYER2: return "Player 2 kovetkezik";
            case PLAYER1_WON: return "Player 1 nyert";
            case PLAYER2_WON: return "Player 2 nyert";
            case STALEMATE: return "Dontetlen";
            case WAITING: return "Varakozas egy masik jatekosra";
        }
        return "";
    }
    
    public GameInterface getGame() {
        if (game == null) {
            setGameId(Long.parseLong(gameId));
        }
        return game;
    }
    
    public void setGameId(long id) {
        InitialContext ic;
        try {
            ic = new InitialContext();
            game = (GameInterface) ic.lookup("java:app/kompgame-game-kamisado-1.0-SNAPSHOT/hu.elte.komp.kamisado");
            game.loadGameId(id);
        } catch (NamingException ex) {
            Logger.getLogger(GameBean.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
    }

    public String getGameId() {
        return gameId;
    }
    
    
    
    public Board getBoard() {
        Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        GameInterface g = getGame();
        Board b = g.getCurrentBoard("hu:" + principal.getName());
        return b; 
    }
    
}

