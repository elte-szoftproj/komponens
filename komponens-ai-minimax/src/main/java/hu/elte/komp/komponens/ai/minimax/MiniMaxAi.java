package hu.elte.komp.komponens.ai.minimax;

import hu.elte.komp.game.AiInterface;
import hu.elte.komp.game.GameGraphInterface;
import java.util.ArrayList;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * MiniMax AI az {@link hu.elte.komp.game.AiInterface AiInterface} szerint.
 * @author davidangeli
 */
@Stateless(name="hu.elte.komp.ai.minimax", mappedName = "hu.elte.komp.ai.minimax")
@LocalBean
public class MiniMaxAi implements AiInterface {
    private static final int MELYSEG=3;

    @Override
    public Object getNextStep(GameGraphInterface gameGraph, boolean youArePlayerOne) {
        
        long max=Long.MIN_VALUE;
        Object nextstep=null;
        ArrayList<Long> nextsteps=new ArrayList<>();
        
        for(Object o: gameGraph.getPossibleSteps(gameGraph.getCurrentStep())) {
            long l=minimax(gameGraph,o,MELYSEG,false);
            nextsteps.add(l);
            if(l>=max) {
                max=l;
                nextstep=o;           
            }
        }
        // ha nem talál lépést, null-lal tér vissza
        return nextstep;        
    }
    
    private long minimax(GameGraphInterface gameGraph, Object node, int m, boolean maximizing){
        //ha levél, értéket kap
        if(m==0 || !gameGraph.getPossibleSteps(node).iterator().hasNext()){
            return gameGraph.getStepScoreForPlayer(node, false);
        }
        
        //egyébként
        else {
            long maxmin=maximizing? Long.MIN_VALUE : Long.MAX_VALUE;            
            for(Object o: gameGraph.getPossibleSteps(node)){
                maxmin=maximizing? Math.max(maxmin, minimax(gameGraph,o,m-1,!maximizing)) : Math.min(maxmin, minimax(gameGraph,o,m-1,!maximizing));                
            }
            return maxmin;
        }

    }
}
