/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp.kompgame.web2;

import hu.elte.komp.GameService;
import hu.elte.komp.game.GameInterface;
import hu.elte.komp.model.Game;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

/**
 *
 * @author evan
 */
@ManagedBean
@ViewScoped
public class JoinGameScreen {
    
    public List<ListItem> getGameList() {
        if (gameList == null) {
            gameList = new ArrayList<>(gameIdList.length + 1);
            for (String id : gameIdList) {
                GameInterface gi = gameService.findGameInterfaceByName(id);
                gameList.add(new ListItem(gameList.size(), gi.getGameTypeName(), "", gi));
            }
        }
        return gameList;
    }

    public List<ListItem> getWaitingList() {
        if (waitingList == null) {
            waitingList = new ArrayList<>();
            for (Game game : gameService.findWaitingGames()) {
                if (gameType == null || gameType.getGameTypeName().equals(game.getTypeName())) {
                    waitingList.add(new ListItem(waitingList.size(), game.getPlayer1().substring(3), "", game));
                }
            }
        }
        return waitingList;
    }
    
    public String join() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        GameInterface gameInterface = gameService.findGameInterfaceByName(waitingGame.getTypeName());

        gameSession.joinGame(waitingGame, gameInterface, request.getRemoteUser());
        
        return "/secure/play-game";
    }
    
    @Transactional
    public String delete() {
        if (waitingGame != null) {
            em.remove(waitingGame);
        }
        waitingGameIndex = -1;
        waitingGame = null;
        waitingList = null;
        return null;
    }
    
    public int getGameTypeIndex() {
        return gameTypeIndex;
    }
    
    public void setGameTypeIndex(int value) {
        if (gameTypeIndex != value) {
            gameTypeIndex = value;
            gameType = gameTypeIndex < 0 ? null : (GameInterface) gameList.get(value).value;
            
            waitingGameIndex = -1;
            waitingGame = null;
            waitingList = null;
        }
    }
    
    public int getWaitingGameIndex() {
        return waitingGameIndex;
    }
    
    public void setWaitingGameIndex(int value) {
        if (waitingGameIndex != value) {
            waitingGameIndex = value;
            waitingGame = waitingGameIndex < 0 ? null : (Game) waitingList.get(value).value;
        }
    }
    
    public GameSession getGameSession() {
        return gameSession;
    }
    
    public void setGameSession(GameSession value) {
        gameSession = value;
    }
    
    private int gameTypeIndex = -1;
    private GameInterface gameType;
    
    private int waitingGameIndex = -1;
    private Game waitingGame;
    
    private final String[] gameIdList = new String[]{"kamisado"};
    
    private List<ListItem> gameList;
    private List<ListItem> waitingList;
    
    @EJB
    private GameService gameService;
    @EJB
    private TransactionalEntityManager em;
    
    @ManagedProperty("#{gameSession}")
    private GameSession gameSession;
}
