/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp.model;

/**
 *
 * @author Zsolt
 */
public enum GameState {
    WAITING,
    
    ONGOING_PLAYER1,
    ONGOING_PLAYER2,
    
    PLAYER1_WON,
    PLAYER2_WON,
    STALEMATE
}
