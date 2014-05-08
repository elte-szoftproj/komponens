/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp;

import hu.elte.komp.game.GameInterface;
import hu.elte.komp.model.AiType;
import hu.elte.komp.model.Game;
import hu.elte.komp.model.GameState;
import hu.elte.komp.util.GameCreator;
import java.io.Serializable;
import java.security.Principal;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author Zsolt
 */
@SessionScoped
@Named(value="gameController")
public class GameController implements Serializable {

    GameCreator gc;
    
    @EJB
    GameService gameService;
        
    private static final long serialVersionUID = 1L;
    
    /**
     * Creates a new instance of GameController
     */
    public GameController() {
        gc = null;
    }
    
    public GameCreator getCurrent() {
        if (gc == null) {
            gc = new GameCreator();
        }
        
        return gc;
    }
    
    public List<AiType> getAiTypes() {
        return gameService.listAllAi();
    }
    
    public Set<String> getScoreTypes() {
        return gameService.getScoreCalculatorNames("kamisado");
    }
    
    public String create() {
        
        Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        String aiPlayer = "ai:" + gc.getAiName() + "|" + gc.getAiScoreCalculator();
        
        GameInterface gi = gameService.findGameInterfaceByName("kamisado");
        
        Game g = gi.createGame(principal.getName());
        g.setPlayer2(aiPlayer);
        g.setGameState(GameState.ONGOING_PLAYER1);
        //gameService.persistGame(g);
         
        return "/secure/game.xhtml?faces-redirect=true&id=" + g.getId();
    }
}
