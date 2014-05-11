/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.komp.kompgame.game.kamisado;

import hu.elte.komp.game.AbstractGame;
import hu.elte.komp.game.Board;
import hu.elte.komp.game.Position;
import hu.elte.komp.game.ScoreCalculator;
import hu.elte.komp.model.Game;
import hu.elte.komp.model.GameState;
import java.util.HashSet;
import java.util.Set;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author martin
 */
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
        BoardHelper.MoveResult clickRes = BoardHelper.clickOn(g.getBoardInfo(), clickPosition, g.isFirstPlayer(player));
        g.setBoardInfo(clickRes.str);
        if (clickRes.nextState != null) {
            g.setGameState(clickRes.nextState);
        }
        getGameService().updateGame(g);
        // SAVE!!!
    }

    @Override
    public void doAiStep() {
        Game g = getEntityInfo();
        // TODO: real action
        g.setGameState(GameState.ONGOING_PLAYER1);
        getGameService().updateGame(g);
    }

    @Override
    public Set<String> getScoreCalculators() {
        Set<String> s = new HashSet<>();
        s.add("random");
        s.add("incremental");
        return s;
    }

    @Override
    public String createBoard() {
        return BoardHelper.getInitialBoard();
    }

    @Override
    protected ScoreCalculator getScoreCalculator(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getCurrentStep() {
        Game g = getEntityInfo();
        return g.getBoardInfo();
    }

    @Override
    public Set<Object> getPossibleSteps(Object step) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

 
}
