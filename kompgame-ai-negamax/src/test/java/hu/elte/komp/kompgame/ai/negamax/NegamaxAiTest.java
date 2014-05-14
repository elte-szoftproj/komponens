/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp.kompgame.ai.negamax;

import hu.elte.komp.game.GameGraphInterface;
import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nrew
 */
public class NegamaxAiTest {
    
    public NegamaxAiTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    NegamaxAi ai = new NegamaxAi();
    NegamaxAi.Node leaf;
    NegamaxAi.Node root;
    
    @Before
    public void setUp() {
        root = new NegamaxAi.Node("", 0L);
        NegamaxAi.Node child;
        for (long i = 1; i <= 3; ++i) {
            child = new NegamaxAi.Node("", i);
            root.addChild(child);
        }
        leaf = new NegamaxAi.Node("", 5L);
        root.addChild(leaf);
    }
    
    @After
    public void tearDown() {
        root = null;
        leaf = null;
    }

    /**
     * Test of getNextStep method, of class NegamaxAi.
     */
    @org.junit.Test
    public void testGetNextStep() throws Exception {
        System.out.println("getNextStep");
//        GameGraphInterface gameGraph = null;
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        NegamaxAi instance = (NegamaxAi)container.getContext().lookup("java:global/classes/NegamaxAi");
        //Object expResult = null;
        //Object result = instance.getNextStep(gameGraph, true);
        //assertEquals(expResult, result);
        
//        container.close();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    @org.junit.Test
    public void testEvaluateTreeLeaf() throws Exception {
        assertEquals(5L, ai.evaluateTree(leaf).longValue());
    }
    
    @org.junit.Test
    public void testEvaluateTreeRoot() throws Exception {
        assertEquals(-1L, ai.evaluateTree(root).longValue());
    }
    
}
