/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp.util;

/**
 *
 * @author Zsolt
 */
public class GameCreator {
    
    private String aiName;
    
    private String aiScoreCalculator;

    public String getAiName() {
        return aiName;
    }

    public void setAiName(String aiName) {
        this.aiName = aiName;
    }

    public String getAiScoreCalculator() {
        return aiScoreCalculator;
    }

    public void setAiScoreCalculator(String aiScoreCalculator) {
        this.aiScoreCalculator = aiScoreCalculator;
    }
 
}
