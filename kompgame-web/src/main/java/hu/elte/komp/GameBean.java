/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp;

import hu.elte.komp.game.Board;
import hu.elte.komp.game.GameInterface;
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
    
    
    GameInterface game;
    
    /**
     * Creates a new instance of komp
     */
    public GameBean() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        this.gameId = facesContext.getExternalContext().getRequestParameterMap().get("id");
        //getGame();
    }
    
    public GameInterface getGame() {
        if (game == null) {
            setGameId(Integer.parseInt(gameId));
        }
        return game;
    }
    
    public void setGameId(long id) {
        InitialContext ic;
        try {
            ic = new InitialContext();
            game = (GameInterface) ic.lookup("java:app/kompgame-game-kamisado-1.0-SNAPSHOT/hu.elte.komp.kamisado");
        } catch (NamingException ex) {
            Logger.getLogger(GameBean.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
    }
    
    public Board getBoard() {
        Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        return getGame().getCurrentBoard("hu:" + principal.getName());
    }
    
}
