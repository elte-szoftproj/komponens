/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp.game;

/**
 *
 * @author Zsolt
 */
public interface ScoreCalculator {
    
    public long getScoreForStep(Object step);
    
}
