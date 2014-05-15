/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.komp.kompgame.game.kamisado;

import hu.elte.komp.game.AbstractGame;
import hu.elte.komp.game.AiInterface;
import hu.elte.komp.game.Board;
import hu.elte.komp.game.GameInterface;
import hu.elte.komp.game.Position;
import hu.elte.komp.game.ScoreCalculator;
import hu.elte.komp.model.AiType;
import hu.elte.komp.model.Game;
import hu.elte.komp.model.GameState;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;


@Stateless(name = "hu.elte.komp.kamisado", mappedName = "hu.elte.komp.kamisado")
@LocalBean
public class KamisadoGame extends AbstractGame {

    @Override
    public String getGameTypeName() {
        return "kamisado";
    }

    @Override
    public Board getCurrentBoard(String player) {
        Game g = getEntityInfo();
        String boardInfo = g.getBoardInfo();
        return BoardHelper.getBoardForString(boardInfo, g.isFirstPlayer(player));
    }

    @Override
    public void clickedOn(String player, Position clickPosition) {
        
        Game g = getEntityInfo();
        
        if (!player.equals(g.getCurrentPlayer()) || (g.getGameState() != GameState.ONGOING_PLAYER1 && g.getGameState() != GameState.ONGOING_PLAYER2)) {
            // we can't do anything if we are not the current player, or if the game ended
            return;
        }
        
        BoardHelper.MoveResult clickRes = BoardHelper.clickOn(g.getBoardInfo(), clickPosition, g.isFirstPlayer(player));
        g.setBoardInfo(clickRes.str);
        if (clickRes.nextState != null) {
            g.setGameState(clickRes.nextState);
        }
        getGameService().updateGame(g);
    }

    @Override
    public void doAiStep() {
        // TODO: move this method to abstractgame!
        Game g = getEntityInfo();
        String[] aiInfo = g.getCurrentPlayerName().split("\\|");
        AiType at = getGameService().findAiByName(aiInfo[0]);
        if (at == null) {
            throw new RuntimeException("Ai not found: " + aiInfo[0]);
        }
        InitialContext ic;
        try {
            ic = new InitialContext();
            AiInterface ai = (AiInterface) ic.lookup(at.getUrl());
            try {
                Object step = ai.getNextStep(this, false);
                if (step == null) {
                    g.setGameState(GameState.PLAYER1_WON);
                } else {
                    doStep(g, step);
                    g.setGameState(GameState.ONGOING_PLAYER1);
                }
            } catch (Exception e) {
                g.setGameState(GameState.PLAYER1_WON);
            }
        } catch (NamingException ex) {
            // AI not found :(
            //g.setGameState(GameState.PLAYER1_WON);
            throw new RuntimeException(ex);
        }
        
        getGameService().updateGame(g);
    }

    @Override
    public Set<String> getScoreCalculators() {
        Set<String> s = new HashSet<>();
        s.add("zero");
        s.add("win");
        return s;
    }

    @Override
    public String createBoard() {
        return BoardHelper.getInitialBoard();
    }

    @Override
    protected ScoreCalculator getScoreCalculator(String name) {
        if ("win".equals(name)) { 
            return new WinCalculator();
        }
        return new ZeroCalculator();
    }

    @Override
    public Object getCurrentStep() {
        Game g = getEntityInfo();
        return new KamisadoAiIterator.StepInfo(g.getBoardInfo(), g.getGameState() == GameState.ONGOING_PLAYER1);
    }
    
    public void doStep(Game g, Object step) {
        if (!(step instanceof KamisadoAiIterator.StepInfo)) {
            throw new RuntimeException("Invalid argument! " + step.getClass().toString());
        }
        final KamisadoAiIterator.StepInfo si = (KamisadoAiIterator.StepInfo) step;
        GameState gs = BoardHelper.getWinningState(si.getBoard());
        if (gs != null) {
            g.setGameState(gs);
        }
        g.setBoardInfo(si.getBoard());
    }

    @Override
    public Iterable getPossibleSteps(Object step) {
        if (!(step instanceof KamisadoAiIterator.StepInfo)) {
            throw new RuntimeException("Invalid argument!");
        }
        final KamisadoAiIterator.StepInfo si = (KamisadoAiIterator.StepInfo) step;
        return new Iterable() {

            @Override
            public Iterator iterator() {
                return BoardHelper.getPossibleSteps(si.getBoard(), si.isNextIsPlayerOne());
            }
            
        };
    }

 
}
