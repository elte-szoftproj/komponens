/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.komp.kompgame.game.tictactoe;

import hu.elte.komp.game.Board;
import hu.elte.komp.game.Position;
import hu.elte.komp.model.GameState;
import java.awt.Color;
import java.util.Iterator;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author user
 */
public class BoardHelperTest {
    
    enum Color {
        BLUE, RED
    }
    
    public BoardHelperTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

  
     

    /**
     * Test of getInitialBoard method, of class BoardHelper.
     */
    @Test
    public void testGetInitialBoard() {
        System.out.println("getInitialBoard");
        //String expResult = "2:2:0000";
        String result = BoardHelper.getInitialBoard();
        assertEquals("2:2:0000", result);
        System.out.println(result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

     
    
}
