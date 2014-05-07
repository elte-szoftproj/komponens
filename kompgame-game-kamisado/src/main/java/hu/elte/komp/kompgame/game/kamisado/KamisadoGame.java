/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.komp.kompgame.game.kamisado;

import hu.elte.komp.game.Board;
import hu.elte.komp.game.GameInterface;
import hu.elte.komp.game.Position;
import hu.elte.komp.game.SimplePiece;
import hu.elte.komp.model.Game;
import java.util.Set;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author martin
 */
@Stateless(name = "hu.elte.komp.kamisado", mappedName = "hu.elte.komp.kamisado")
@LocalBean
public class KamisadoGame implements GameInterface {

    @Override
    public String getGameTypeName() {
        return "kamisado";
    }

    @Override
    public Game getEntityInfo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void loadGameId(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Board getCurrentBoard(String player) {
        
        // displays the initial board for now
        return BoardHelper.getBoardForString(BoardHelper.getInitialBoard());
    }

    @Override
    public void clickedOn(String player, Position clickPosition) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doAiStep() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<String> getScoreCalculators() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
