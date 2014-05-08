/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp.game;

import hu.elte.komp.model.Game;
import hu.elte.komp.model.GameState;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Zsolt
 */
public abstract class AbstractGame implements GameInterface, GameGraphInterface {

    @PersistenceContext(unitName="hu.elte.komp_kompgame-pu")
    private EntityManager em;
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
    public long getStepScoreForCurrentPlayer(Object step) {
        // TODO
        return 0;
    }

    @Override
    public void loadGameId(long id) {
        gameId = id;
    }
    
    @Override
    public Game getEntityInfo() {
        return em.find(Game.class, gameId);
    }

    @Override
    public Game createGame(String firstPlayer) {
        Game g = new Game();
        g.setBoardInfo(createBoard());
        g.setGameState(GameState.WAITING);
        g.setLastStepAt(new Date());
        em.persist(g);
        
        return g;
    }

    protected abstract String createBoard();
    
}
