/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp.game;

import hu.elte.komp.model.Game;
import java.util.Set;

public interface GameInterface {
        
    /**
     * Visszaadja a jatek tipusat
     * 
     * @return 
     */
    public String getGameTypeName();
    
    /**
     * Visszaadja a jatek adatbazi  srekordjat
     * @return 
     */
    public Game getEntityInfo();
    
    /**
     * Betolti a jatekot az idje alapjan
     * @param id 
     */
    public void loadGameId(long id);
    
    /**
     * Visszaadja a kirajzolando palyat
     * @param player
     * @return 
     */
    public Board getCurrentBoard(String player);
    
    /**
     * Meghivando, ha a jatekos klikkelt valahova. A klikkeles alapjan (ha az 
     * valid) modositja a palyat. Ha nem az, exceptiont dob.
     * 
     * @param player
     * @param clickPosition A kattintas pozicioja, a boardon indexelve, 0-tol kezdve
     */
    public void clickedOn(String player, Position clickPosition);
    
    /**
     * Vegrehajtja az MI lepeset
     */
    public void doAiStep();
    
    public Set<String> getScoreCalculators();
    
    public Game createGame(String firstPlayer);
}
