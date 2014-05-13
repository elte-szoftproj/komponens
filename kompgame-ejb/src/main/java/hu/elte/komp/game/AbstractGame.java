/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp.game;

import hu.elte.komp.GameService;
import hu.elte.komp.model.Game;
import hu.elte.komp.model.GameState;
import java.util.Date;
import javax.ejb.EJB;

/**
 *
 * @author Zsolt
 */
public abstract class AbstractGame implements GameInterface, GameGraphInterface {


    @EJB
    private GameService gameService;
    private long gameId;
    
    @Override
    public void doAiStep() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        
    /**
     * Visszaadja az adott nevhez tartozo pontszamito osztalyt.
     * @param name
     * @return 
     */
    protected abstract ScoreCalculator getScoreCalculator(String name);
    
    
    /**
     * Megmondja, mekkora az adott erteke, ha a jatekos AI. Ha human, 0-val ter vissza.
     * @param step
     * @return 
     */
    @Override
    public long getStepScoreForPlayer(Object step, boolean forPlayerOne) {
        if (!getEntityInfo().isCurrentPlayerAi()) {
            return 0;
        }
        String[] p = getEntityInfo().getCurrentPlayerName().split("|");
        
        ScoreCalculator sc = getScoreCalculator(p[1]);
        return sc.getScoreForStep(step);
    }

    @Override
    public void loadGameId(long id) {
        gameId = id;
    }
    
    @Override
    public Game getEntityInfo() {
        return gameService.findGameById(gameId);
    }

    @Override
    public Game createGame(String firstPlayer) {
        Game g = new Game();
        g.setBoardInfo(createBoard());
        g.setGameState(GameState.WAITING);
        g.setLastStepAt(new Date());
        g.setPlayer1("hu:" + firstPlayer);
        gameService.persistGame(g);
        
        return g;
    }

    protected abstract String createBoard();

    public GameService getGameService() {
        return gameService;
    }
    
    
}
