/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp.komponens.ai.alfabeta;

import hu.elte.komp.game.GameGraphInterface;
import java.util.HashSet;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author angelid
 */
public class AlfaBetaAiTest {
    final static Random rnd=new Random();
    AlfaBetaAi ABTest;
    Node gyoker;
    final static int level=3;
    final static int maxchild=4;
    
    public AlfaBetaAiTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        ABTest=new AlfaBetaAi();
        gyoker=new Node();
        createGraph(gyoker, level, maxchild);        
    }  

    
    @After
    public void tearDown() {
    }

    /**
     * Test of getNextStep method, of class AlfaBetaAi.
     */
    @Test
    public void testGetNextStep() throws Exception {
        System.out.println("getNextStep");
        GameGraphInterface gameGraph = gyoker;
        boolean youArePlayerOne = false;        
        
        printRoute(gyoker,"root:");
        Object result = ABTest.getNextStep(gameGraph, youArePlayerOne);
        assert gyoker.children.contains((Node)result);
        System.out.println("Next step found: "+((Node)result).score);
    }
    
    private void createGraph(Node nd, int l, int m){
        for (int i=0;i<l;++i){
            int mc=new Random().nextInt(m+1);
            for (int j=0;j<mc;++j){
                Node n=new Node();
                nd.addChild(n);
                createGraph(n,l-1,m);
            }
        }
    }
    
    private void printRoute(Node nd, String route){
        String rt=route+ " " + nd.score;
        for (Node n:nd.children){            
            if (n.children.isEmpty()) {
                System.out.println(rt);
                return;
            }
            else {
                printRoute(n,rt);
            }
        }    
    }
    
    private class Node implements GameGraphInterface {
        
        private Long score;        
        private final HashSet<Node> children;
        
        Node() {
            this.score = new Long(rnd.nextInt(100));
            this.children = new HashSet<Node>() {};
        }

        public void addChild(Node node) {
            children.add(node);
        }
        
        public Long getScore() {
            return this.score;
        }

        @Override
        public Object getCurrentStep() {
            return this;
        }

        @Override
        public Iterable getPossibleSteps(Object step) {
            return children;
        }

        @Override
        public long getStepScoreForPlayer(Object step, boolean forPlayerOne) {
            return this.score;
        }
    }
    
}
