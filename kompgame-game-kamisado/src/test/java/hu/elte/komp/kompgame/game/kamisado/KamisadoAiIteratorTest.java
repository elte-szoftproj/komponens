/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp.kompgame.game.kamisado;

import java.util.Iterator;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class KamisadoAiIteratorTest {
    
    KamisadoAiIterator ai;
    
    public KamisadoAiIteratorTest() {
    }
    
    @Before
    public void setUp() {
        ai = new KamisadoAiIterator( "ijklmnop" +
                "        " +
                "        " +
                "        " +
                "        " +
                "        " +
                "        " +
                "abcdefgh ", false);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testEnds() {
        int i = 0;
        for(Object o: new Iterable() {

            @Override
            public Iterator iterator() {
                return ai;
            }
        }) {
            i++;
            if (i % 10 == 0) {
                System.out.println("Progress: " + i + ((KamisadoAiIterator.StepInfo)o).getBoard());
            }
        }
    }
}
