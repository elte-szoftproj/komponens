/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.komp.komponens.ai.alfabeta;

import hu.elte.komp.game.AiInterface;
import hu.elte.komp.game.GameGraphInterface;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author davidangeli
 */
@Stateless(name="hu.elte.komp.ai.alfabeta", mappedName = "hu.elte.komp.ai.alfabeta")
@LocalBean
public class AlfaBetaAi implements AiInterface {
    private static final int MELYSEG=2;

    @Override
    public Object getNextStep(GameGraphInterface gameGraph, boolean youArePlayerOne) {
        
        long max=Long.MIN_VALUE;
        Object nextstep=null;
        
        for(Object o: gameGraph.getPossibleSteps(gameGraph.getCurrentStep())) {
            long l=alfabeta(gameGraph,o,MELYSEG,false,Long.MIN_VALUE,Long.MAX_VALUE);
            if(l>=max) {
                max=l;
                nextstep=o;           
            }
        }
        // ha keveset lephetunk, es tul nagy random? feladjuk...
        return nextstep;        
    }
    
    private long alfabeta (GameGraphInterface gameGraph, Object node, int m, boolean maximizing, long alpha, long beta){
        long al=alpha, be=beta;
        //ha level, erteket kap
        if (m==0 || !gameGraph.getPossibleSteps(node).iterator().hasNext()){
          return gameGraph.getStepScoreForPlayer(node, false);
        }
        
        if (maximizing){
            for(Object o: gameGraph.getPossibleSteps(node)){
                be = Math.min(be, alfabeta(gameGraph, o, m-1, !maximizing, al, be));
                if (be<=al) return al;
            }
            return be;
        }
        else {
            for(Object o: gameGraph.getPossibleSteps(node)){
                al = Math.max(al, alfabeta(gameGraph, o, m-1, !maximizing, al, be));
                if (be<=al) return be;
            }
            return al; 
        }
   }

}

