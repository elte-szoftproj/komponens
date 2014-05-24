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
import hu.elte.komp.model.Game;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author Six
 */
@Stateless
@LocalBean
public class GameTypeService {
    @PersistenceContext(unitName="hu.elte.komp_kompgame-pu")
    private EntityManager em;
    
    public List<GameType> getGameTypes(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<GameType> cq = cb.createQuery(GameType.class);
        Root<GameType> rootEntry = cq.from(GameType.class);
        CriteriaQuery<GameType> all;
        all = cq.select(rootEntry);
        TypedQuery<GameType> allQuery = em.createQuery(all);
        List<GameType> found = allQuery.getResultList();
        
        if (found.isEmpty()) {
            return null;
        }
        
        return found;
    }
}
