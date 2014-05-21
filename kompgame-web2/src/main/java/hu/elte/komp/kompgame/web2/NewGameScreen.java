/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp.kompgame.web2;

import hu.elte.komp.GameService;
import hu.elte.komp.game.GameInterface;
import hu.elte.komp.model.AiType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@ViewScoped
public class NewGameScreen implements Serializable {
        
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
    
    public List<ListItem> getAiList() {
        if (aiList == null) {
            List<AiType> list = gameService.listAllAi();
            aiList = new ArrayList<>(list.size() + 1);
            aiList.add(new ListItem(0, "Human", "", null));
            for (AiType ai : list) {
                aiList.add(new ListItem(aiList.size(), "Ai: " + ai.getName(), "", ai));
            }
        }
        return aiList;
    }
        
    public List<ListItem> getScoreList() {
        if (scoreList == null) {
            scoreList = new ArrayList<>();
            if (gameType != null) {
                for (String sc : gameType.getScoreCalculators()) {
                    scoreList.add(new ListItem(scoreList.size(), sc, "", sc));
                }
            }
        }
        return scoreList;
    }
        
    public String create() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        gameSession.newGame(gameType.createGame(request.getRemoteUser()), gameType, request.getRemoteUser());
        
        return "/secure/play-game";
    }
    
    public int getGameTypeIndex() {
        return gameTypeIndex;
    }
    
    public void setGameTypeIndex(int value) {
        if (gameTypeIndex != value) {
            gameTypeIndex = value;
            gameType = (gameTypeIndex < 0) ? null : (GameInterface) gameList.get(value).value;

            scoreTypeIndex = -1;
            scoreType = null;
            scoreList = null;
        }
    }
    
    public void validateGameTypeIndex(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        int index = (Integer) value;
        
        if (index < 0 | index > gameList.size()) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, null, null));
        }
    }

    public void validateAiTypeIndex(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        int index = (Integer) value;
        
        if (index < 0 | index > aiList.size()) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, null, null));
        }
    }
    
    public void validateScoreTypeIndex(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        int index = (Integer) value;
        
        if (index < 0 | index > scoreList.size()) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, null, null));
        }
    }

    public int getAiTypeIndex() {
        return aiTypeIndex;
    }
    
    public void setAiTypeIndex(int value) {
        if (aiTypeIndex != value) {
            aiTypeIndex = value;
            aiType = (aiTypeIndex < 0) ? null : (AiType) aiList.get(value).value;
        }
    }
    
    public int getScoreTypeIndex() {
        return scoreTypeIndex;
    }
    
    public void setScoreTypeIndex(int value) {
        if (scoreTypeIndex != value) {
            scoreTypeIndex = value;
            scoreType = (scoreTypeIndex < 0) ? null : (String) scoreList.get(value).value;
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
    private int aiTypeIndex = -1;
    private AiType aiType;
    private int scoreTypeIndex = -1;
    private String scoreType;
    
    private final String[] gameIdList = new String[]{"kamisado"};
    
    private List<ListItem> gameList;
    private List<ListItem> aiList;
    private List<ListItem> scoreList;

    @EJB
    private GameService gameService;
    
    @ManagedProperty("#{gameSession}")
    private GameSession gameSession;
}
