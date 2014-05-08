/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp;

import hu.elte.komp.util.GameCreator;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Zsolt
 */
@SessionScoped
@Named("gameController")
public class GameController implements Serializable {

    GameCreator gc;
    
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
    
    public void create() {
        
    }
}
