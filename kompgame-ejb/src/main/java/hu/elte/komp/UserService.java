/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.komp;

import hu.elte.komp.model.Group;
import hu.elte.komp.model.User;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author martin
 */
@Stateless
@LocalBean
public class UserService {

    @PersistenceContext(unitName="hu.elte.komp_kompgame-pu")
    private EntityManager em;

    
    public void createUserAndGroup(String username, String password, String groupname) {
         User user = new User();
         user.setUsername(username);
         String encodedPassword = encodePassword(password);
         user.setPassword(encodedPassword);
         em.persist(user);
         
         Group group = new Group();
         group.setName(groupname);
         group.setUser(user);
         em.persist(group);
    }

    private String encodePassword(String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
