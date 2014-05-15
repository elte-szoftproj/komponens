package hu.elte.komp.kompgame.game.test.tictactoe;

import hu.elte.komp.game.AbstractGame;
import hu.elte.komp.game.AiInterface;
import hu.elte.komp.game.Board;
import hu.elte.komp.game.Position;
import hu.elte.komp.game.ScoreCalculator;
import hu.elte.komp.model.AiType;
import hu.elte.komp.model.Game;
import hu.elte.komp.model.GameState;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Stateless(name = "hu.elte.komp.tictactoe-test", 
        mappedName = "hu.elte.komp.tictactoe-test")
@LocalBean
public class TicTacToe extends AbstractGame {

    public static final char FIRST_SYMBOL = 'X';
    public static final char SECOND_SYMBOL = 'O';
    
    private final BoardHelper boardHelper;
    
    public TicTacToe() {
        boardHelper = new BoardHelper();
    }
    
    @Override
    public String getGameTypeName() {
        return "tic-tac-toe-test";
    }

    @Override
    public Board getCurrentBoard(String player) {
        Game game = getEntityInfo();
        String boardInfo = game.getBoardInfo();
        return boardHelper.getBoardFromString(boardInfo, 
                game.isFirstPlayer(player));
    }

    @Override
    public void clickedOn(String player, Position clickPosition) {
        Game game = getEntityInfo();
        GameState gameState = game.getGameState();
        
        if (!player.equals(game.getCurrentPlayer()) || 
                (gameState != GameState.ONGOING_PLAYER1 && 
                gameState != GameState.ONGOING_PLAYER2)) {
            return;
        }
        
        StringBuilder boardInfo = new StringBuilder(game.getBoardInfo());
        int position = clickPosition.getX() * 3 + clickPosition.getY();
        char playerSymbol = gameState == GameState.ONGOING_PLAYER1 ? 
                FIRST_SYMBOL : SECOND_SYMBOL;
        if (boardInfo.charAt(position) == ' ') {
            boardInfo.setCharAt(position, playerSymbol);
            String updatedBoard = boardInfo.toString();
            game.setBoardInfo(updatedBoard);
            if (boardHelper.isWinningState(updatedBoard)) {
                if (gameState == GameState.ONGOING_PLAYER1) {
                    game.setGameState(GameState.PLAYER1_WON);
                } else {
                    game.setGameState(GameState.PLAYER2_WON);
                }
            } else if (boardHelper.isSlalemate(updatedBoard)){
                game.setGameState(GameState.STALEMATE);
            } else {
                if (gameState == GameState.ONGOING_PLAYER1) {
                    game.setGameState(GameState.ONGOING_PLAYER2);
                } else {
                    game.setGameState(GameState.ONGOING_PLAYER1);
                }
            }
            getGameService().updateGame(game);
        }
    }
    
    @Override
    public void doAiStep() {
               // TODO: move this method to abstractgame!
        Game game = getEntityInfo();
        String[] aiInfo = game.getCurrentPlayerName().split("\\|");
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
                    game.setGameState(GameState.PLAYER1_WON);
                } else {
                    doStep(game, step);
                }
            } catch (Exception e) {
                game.setGameState(GameState.PLAYER1_WON);
                throw e;
            }
        } catch (Exception ex) {
            // AI not found :(
            g.setGameState(GameState.PLAYER1_WON);
        }
        
        try {
        getGameService().updateGame(game);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Set<String> getScoreCalculators() {
        Set<String> set = new HashSet<>();
        set.add("Winner Scoring");
        return set;
    }

    @Override
    protected String createBoard() {
        return boardHelper.getInitialBoard();
    }

    @Override
    protected ScoreCalculator getScoreCalculator(String name) {
        return new WinnerScoringCalculator();
    }
    
    @Override
    public long getStepScoreForPlayer(Object step, boolean isAiStep) {
        
        if (!getEntityInfo().isCurrentPlayerAi()) {
            return 0;
        }
        
        String board = (String) step;
        
        if (boardHelper.isWinningState(board)) {
            if (isAiStep) {
                return 10;
            } 
            return -10;
        }
        
        return 0;
    }

    @Override
    public Object getCurrentStep() {
        Game game = getEntityInfo();
        return game.getBoardInfo();
    }

    @Override
    public Iterable getPossibleSteps(Object step) {
        final String board = (String) step;
        return new Iterable() {
            
            @Override
            public Iterator iterator() {
                return new TicTacToeStateIterator(board);
            }
        };
    }

    public void doStep(Game game, Object step) {
    if (!(step instanceof String)) {
        throw new RuntimeException("Invalid argument! " + step.getClass().toString());
    }
    final String board = (String) step;
    GameState gameState = game.getGameState();
    if (boardHelper.isWinningState(board)) {
                if (gameState == GameState.ONGOING_PLAYER1) {
                    game.setGameState(GameState.PLAYER1_WON);
                } else {
                    game.setGameState(GameState.PLAYER2_WON);
                }
    } else if (boardHelper.isSlalemate(board)){
                game.setGameState(GameState.STALEMATE);
    } else {
                if (gameState == GameState.ONGOING_PLAYER1) {
                    game.setGameState(GameState.ONGOING_PLAYER2);
                } else {
                    game.setGameState(GameState.ONGOING_PLAYER1);
                }
    }
    game.setBoardInfo(board);
}
    
}
