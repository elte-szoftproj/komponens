/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.komp.komponens.ai.random;

import hu.elte.komp.game.AiInterface;
import hu.elte.komp.game.GameGraphInterface;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author martin
 */
@Stateless(name="hu.elte.komp.ai.random", mappedName = "hu.elte.komp.ai.random")
@LocalBean
public class RandomAi implements AiInterface {

    @Override
    public Object getNextStep(GameGraphInterface gameGraph) {
        
        int i = 0;
        int lookfor = (int)(Math.random() * 20);
        
        for(Object o: gameGraph.getPossibleSteps(gameGraph.getCurrentStep())) {
            i++;
            // nop, just to the if it works
            gameGraph.getStepScoreForCurrentPlayer(o);
            if (i == lookfor) {
                return o;
            }
        }
            
        // ha keveset lephetunk, es tul nagy random? feladjuk...
        return null;
        
    }

}
