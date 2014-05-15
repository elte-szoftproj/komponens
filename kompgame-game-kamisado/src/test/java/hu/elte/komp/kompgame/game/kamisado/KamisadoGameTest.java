/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.komp.kompgame.game.kamisado;

import hu.elte.komp.game.Board;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class KamisadoGameTest {
	
	KamisadoGame game;
	
	public KamisadoGameTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
		game = new KamisadoGame();
	}
	
	@After
	public void tearDown() {
	}
	// TODO add test methods here.
	// The methods must be annotated with annotation @Test. For example:
	//
	 @Test
	 public void name() {		 
		 assertEquals("kamisado", game.getGameTypeName());
	 }
	 
	 @Test
	 public void board() {		 
		 //Board b = game.getCurrentBoard("Player 1");		 
	 }
}
