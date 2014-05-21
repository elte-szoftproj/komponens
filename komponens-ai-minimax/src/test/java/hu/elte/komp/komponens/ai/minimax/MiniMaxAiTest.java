/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp.komponens.ai.minimax;

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
public class MiniMaxAiTest {
    final static Random RND=new Random();
    final static int GRAPHLEVEL=3;
    final static int MAXCHILDS=2;
    static int nodeID;
    
    MiniMaxAi ABTest;
    Node gyoker;
    
    public MiniMaxAiTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        ABTest=new MiniMaxAi();
        gyoker=new Node();
        createGraph(gyoker, GRAPHLEVEL, MAXCHILDS);        
    }  

    
    @After
    public void tearDown() {
    }

    /**
     * Test of getNextStep method, of class MiniMaxAi.
     */
    @Test
    public void testGetNextStep() throws Exception {
        System.out.println("getNextStep");
        GameGraphInterface gameGraph = gyoker;
        boolean youArePlayerOne = false;        
        
        printRoute(gyoker,"root:");
        Object result = ABTest.getNextStep(gameGraph, youArePlayerOne);
        assert gyoker.children.contains((Node)result);
        System.out.println("Next step found: "+result);
    }
    
    private void createGraph(Node nd, int l, int m){
        for (int i=0;i<l;++i){
            int mc=RND.nextInt(m+1);            
            for (int j=0;j<mc;++j){
                Node n=new Node();
                nd.addChild(n);
                createGraph(n,l-1,m);
            }
        }
    }
    
    private void printRoute(Node nd, String route){
        String rt=route+ " " + nd;
        if (nd.children.isEmpty()) {
            System.out.println(rt);
        }
        else {
            for (Node n:nd.children){
                printRoute(n,rt);
            }    
        }
    }
    
    private class Node implements GameGraphInterface {
        
        private String name;
        private Long score;        
        private final HashSet<Node> children;
        
        Node() {
            this.name="n"+(++nodeID);
            this.score = new Long(RND.nextInt(100)+1);
            this.children = new HashSet<Node>() {};
        }

        public void addChild(Node node) {
            children.add(node);
        }
        
        @Override
        public String toString() {
            return children.isEmpty()? this.name+":"+this.score : this.name;
        }

        @Override
        public Object getCurrentStep() {
            return this;
        }

        @Override
        public Iterable getPossibleSteps(Object step) {
            return ((Node)step).children;
        }

        @Override
        public long getStepScoreForPlayer(Object step, boolean forPlayerOne) {
            return ((Node)step).score;
        }
    }
    
}
