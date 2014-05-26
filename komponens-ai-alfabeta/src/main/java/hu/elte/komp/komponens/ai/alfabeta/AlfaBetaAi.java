package hu.elte.komp.komponens.ai.alfabeta;

import hu.elte.komp.game.AiInterface;
import hu.elte.komp.game.GameGraphInterface;
import java.util.ArrayList;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * AlfaBeta AI az {@link hu.elte.komp.game.AiInterface AiInterface} szerint.
 * @author davidangeli
 */
@Stateless(name="hu.elte.komp.ai.alfabeta", mappedName = "hu.elte.komp.ai.alfabeta")
@LocalBean
public class AlfaBetaAi implements AiInterface {
    private static final int MELYSEG=5;

    @Override
    public Object getNextStep(GameGraphInterface gameGraph, boolean youArePlayerOne) {
        
        long max=Long.MIN_VALUE;
        Object nextstep=null;
//        ArrayList<Long> nextsteps=new ArrayList<>();
        
        for(Object o: gameGraph.getPossibleSteps(gameGraph.getCurrentStep())) {
            long l=alfabeta(gameGraph,o,MELYSEG,false,Long.MIN_VALUE,Long.MAX_VALUE);
//            nextsteps.add(l);
            if(l>=max) {
                max=l;
                nextstep=o;           
            }
        }
        // ha nem talál lépést, null-lal tér vissza
        return nextstep;
    }
    
    private long alfabeta (GameGraphInterface gameGraph, Object node, int m, boolean maximizing, long alpha, long beta){
        long al=alpha, be=beta;
        //ha levél, értéket kap
        if (m==0 || !gameGraph.getPossibleSteps(node).iterator().hasNext()){
          return gameGraph.getStepScoreForPlayer(node, false);
        }
        
        //egyébként
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

