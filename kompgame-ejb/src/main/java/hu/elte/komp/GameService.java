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
import hu.elte.komp.model.GameType;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Zsolt
 */
@Stateless
@LocalBean
public class GameService {

    
    @PersistenceContext(unitName="hu.elte.komp_kompgame-pu")
    private EntityManager em;
    
    public Game findGameById(long id) {
        return em.find(Game.class, id);
    }
    
    public GameType findGameTypeByName(String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<GameType> cq = cb.createQuery(GameType.class);
        Root<GameType> rootEntry = cq.from(GameType.class);
        CriteriaQuery<GameType> all;
        all = cq.select(rootEntry).where(cb.equal(rootEntry.get("name"), name));
        TypedQuery<GameType> allQuery = em.createQuery(all);
        List<GameType> found = allQuery.getResultList();
        
        if (found.isEmpty()) {
            return null;
        }
        
        return found.get(0);
    }
    
    public GameInterface findGameInterfaceByName(String name) {
        GameType gt = findGameTypeByName(name);
        if (gt == null) {
            return null;
        }
        
        InitialContext ic;
        try {
            ic = new InitialContext();
            return (GameInterface) ic.lookup(gt.getUrl());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public void persistGame(Game game) {
        em.persist(game);
    }
    
    public List<AiType> listAllAi() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AiType> cq = cb.createQuery(AiType.class);
        Root<AiType> rootEntry = cq.from(AiType.class);
        CriteriaQuery<AiType> all = cq.select(rootEntry);
        TypedQuery<AiType> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }
    
    public AiType findAiByName(String name) {
        for(AiType ai: listAllAi()) {
           if (ai.getName().equals(name)) {
               return ai;
           }
        }
        return null;
    }
    
    public Set<String> getScoreCalculatorNames(String name) {
        return findGameInterfaceByName(name).getScoreCalculators();
    }

    public void updateGame(Game g) {
        em.merge(g);
    }
}
