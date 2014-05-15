/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.komp.komponens.ai.minimax;

import hu.elte.komp.game.AiInterface;
import hu.elte.komp.game.GameGraphInterface;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author davidangeli
 */
@Stateless(name="hu.elte.komp.ai.minimax", mappedName = "hu.elte.komp.ai.minimax")
@LocalBean
public class MiniMaxAi implements AiInterface {
    private static final int MELYSEG=1;

    @Override
    public Object getNextStep(GameGraphInterface gameGraph, boolean youArePlayerOne) {
        
        long max=Long.MIN_VALUE;
        Object nextstep=null;
        
        for(Object o: gameGraph.getPossibleSteps(gameGraph.getCurrentStep())) {
            long l=minimax(gameGraph,o,MELYSEG,false);
            if(l>=max) {
                max=l;
                nextstep=o;           
            }
        }
        // ha keveset lephetunk, es tul nagy random? feladjuk...
        return nextstep;        
    }
    
    private long minimax(GameGraphInterface gameGraph, Object node, int m, boolean maximizing){
        //ha level, erteket kap
        if(m==0 || !gameGraph.getPossibleSteps(node).iterator().hasNext()){
            return gameGraph.getStepScoreForPlayer(node, false);
        }
        
        else {
            long maxmin=maximizing? Long.MIN_VALUE : Long.MAX_VALUE;            
            for(Object o: gameGraph.getPossibleSteps(node)){
                maxmin=maximizing? Math.max(maxmin, minimax(gameGraph,o,m-1,!maximizing)) : Math.min(maxmin, minimax(gameGraph,o,m-1,!maximizing));                
            }
            return maxmin;
        }

    }
}
