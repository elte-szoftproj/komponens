/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp.kompgame.web2;

import hu.elte.komp.model.User;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@ManagedBean
@RequestScoped
public class LoginScreen implements Serializable {
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String value) {
        username = value;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String value) {
        password = value;
    }
    
    public String login(boolean redirect) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            request.login(username, password);
        } catch (ServletException ex) {
            context.addMessage(null, new FacesMessage("Login failed!"));
            return null;
        }
        if (redirect) {
            return "/index";
        } else {
            return null;
        }
    }
    
    public String logout(boolean redirect) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            if (request.getRemoteUser() != null) {
                request.logout();
            }
        } catch (ServletException ex) {
            context.addMessage(null, new FacesMessage("Logout failed!"));
            return null;
        }
        if (redirect) {
            return "/index";
        } else {
            return null;
        }
    }
    
    @Transactional
    public String register(boolean redirect) {
        FacesContext context = FacesContext.getCurrentInstance();
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        
        try {
            em.persist(user);        
        } catch (RuntimeException ex) {
            context.addMessage(null, new FacesMessage("Register failed!"));
            return null;
        }
        
        if (redirect) {
            return "/login/login";
        } else {
            return null;
        }
    }
    
    private String username = "";
    private String password = "";
    
    //@PersistenceContext(unitName = "hu.elte.komp_kompgame-pu")
    //private EntityManager em;
    
    @EJB
    private TransactionalEntityManager em;
}
