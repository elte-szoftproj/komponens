/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

/**
 *
 * Egy jatek aktualis allapotat szemlelteti az adatbazisban
 * 
 * @author Zsolt
 */
@Entity
public class Game implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private String version;
    
    /**
     * A jatektipus neve, ez alapjan azonosithato, mihez tartozik
     */
    private String typeName;
    
    /**
     * A jatek tablajanak aktualis reprezentacioja (max 256 karakter)
     */
    private String boardInfo;
    
    /**
     * Az egyik jatekos leirasa. A formatuma (mi|hu):[a-zA-Z0-9|]
     * Az human jatekosok eseten (pl. hu:testuser) a kettospont utan a user
     * neve van.
     * Az MI jatekosok (pl. ai:random|zero) a formatum algoritmus|ertekelo.
     */
    private String player1;
    
    /**
     * A masodik jatekos azonositoja. Lasd az elozo kommentje.
     */
    private String player2;
    
    /**
     * Mikor tortent a legutobbi lepes
     */
    private Date lastStepAt;
    
    /**
     * Hany masodpercig lehet gondolkodni egy lepesen. 0 = vegtelen
     */
    private int timeoutInSeconds;
    
    /**
     * Hany lepesbol allhat maximum egy jatek. 0 = vegtelen
     */
    private int maxSteps;
    
    /**
     * Hany lepesbol all eddig a jatek
     */
    private int currentSteps;
    
    /**
     * A jatek aktualis allapota egy enumban. Bovebb info, lasd enum doksi.
     */
    private GameState gameState;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Game)) {
            return false;
        }
        Game other = (Game) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hu.elte.komp.model.Game[ id=" + id + " ]";
    }

    public String getVersion() {
        return version;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getBoardInfo() {
        return boardInfo;
    }

    public void setBoardInfo(String boardInfo) {
        this.boardInfo = boardInfo;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public Date getLastStepAt() {
        return lastStepAt;
    }

    public void setLastStepAt(Date lastStepAt) {
        this.lastStepAt = lastStepAt;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
    
    public String getCurrentPlayer() {
        switch (this.gameState) {
            case ONGOING_PLAYER1: return player1;
            case ONGOING_PLAYER2: return player2;
            default: return null;
        }
    }
    
    public boolean isCurrentPlayerAi() {
        return (getCurrentPlayer() == null) ? false : getCurrentPlayer().startsWith("ai:");
    }
    
    public String getCurrentPlayerName() {
        return (getCurrentPlayer() == null) ? null : getCurrentPlayer().split(":")[0];
    }
    
    public boolean isCurrentPlayer(String principal) {
        return ("hu:" + principal).equals(getCurrentPlayer());
    }
    
    public boolean isFirstPlayer(String principal) {
        return ("hu:" + principal).equals(getPlayer1());
    }
}
