package com.arvind.assignment.kalah.service;

import com.arvind.assignment.kalah.exception.InvalidPitException;
import com.arvind.assignment.kalah.domain.Game;
import com.arvind.assignment.kalah.domain.Player;
import com.arvind.assignment.kalah.utils.Constants;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GameFacade {

    /**
     * This is main method of the game which will do the move.
     * Will check the validity of move
     * Adding stones to right pit
     * Deciding which player turn is now
     * Checking the game status and declaring the winner
     * @param pitId
     * @param game
     */
    public void makeMove(int pitId, Game game){
        //before making the move, check for the all the valid move
        validateMove(pitId, game);


        //start the move
        Map<Integer, Integer> gameBoard = game.getGameBoard();
        int stones = gameBoard.get(pitId);
        int lastIndex = pitId + stones;
        for (int i = pitId+1; i <= lastIndex; i++){
            if(i == Constants.LAST_PIT_INDEX){
                if(i == game.getPlayer().getKalahId()){
                    addStonesToPit(i, 1, gameBoard);
                    lastIndex = lastIndex - i;
                }else{
                    lastIndex = lastIndex - (i-1);
                }
                i = 0;
                continue;
            }
            if(i == game.getPlayer().getOppositePlayer().getKalahId()){
                lastIndex = lastIndex + 1;
                continue;
            }
            addStonesToPit(i, 1, gameBoard);
        }
        gameBoard.put(pitId, 0);
        //check the last pit
        checkLastPit(lastIndex, game);

        //check if current player has another turn else change the player
        if(lastIndex != game.getPlayer().getKalahId()){
            game.setPlayer(game.getPlayer().getOppositePlayer());
        }

        //check if game is over. If yes, declare the winner
        if(checkIfGameOver(game)){
            game.setStatus(Game.Status.OVER);
            int firstPlayerStones = gameBoard.get(Player.FIRST_PLAYER.getKalahId());
            int secondPlayerStones = gameBoard.get(Player.SECOND_PLAYER.getKalahId());

            if(firstPlayerStones > secondPlayerStones){
                game.setWinner("First Player");
            } else if(firstPlayerStones < secondPlayerStones){
                game.setWinner("Second Player");
            }else{
                game.setWinner("Draw");
            }
        }

    }

    /**
     * Check if move is valid or not. If not valid throw the exception
     * @param pitId
     * @param game
     */
    private void validateMove(int pitId, Game game){
        Player player = game.getPlayer();
        if(player.getKalahId() == pitId || player.getOppositePlayer().getKalahId() == pitId){
            throw new InvalidPitException("You can not select a Kalah as pit");
        }

        if(game.getGameBoard().get(pitId) == 0){
            throw new InvalidPitException("This pit is empty.");
        }

        if(!player.getPits().contains(pitId)){
            throw new InvalidPitException("This pit is not your pit. Your pits are: ", player.getPits());
        }

        if(pitId < Constants.FIRST_PIT_INDEX || pitId > Constants.LAST_PIT_INDEX){
            throw new InvalidPitException("Invalid pit. Pits are only from 1 -> 6 and 8 -> 13. Your pits are: ", player.getPits());
        }

    }

    /**
     * check if last pit if own pit and has only one stone.
     * Then take all the stones from opposite pit and put in your kalah
     * @param pitId
     * @param game
     */
    private void checkLastPit(int pitId, Game game){
        Map<Integer, Integer> gameBoard = game.getGameBoard();
        if(game.getPlayer().getPits().contains(pitId) && gameBoard.get(pitId) == 1){
            int oppositePit = Constants.LAST_PIT_INDEX - pitId;
            int oppositeStones = gameBoard.get(oppositePit);
            if(oppositeStones != 0) {
                //clear your and opposite pit and put stones in your kalah
                gameBoard.replace(pitId, 0);
                gameBoard.replace(oppositePit, 0);
                addStonesToPit(game.getPlayer().getKalahId(), oppositeStones+1, gameBoard);
            }
        }

    }

    /**
     * this method will check if game is over. Then move all the remaining stones of respective players to their Kalah
     * @param game
     */
    private boolean checkIfGameOver(Game game){
        boolean isCurrentPlayerPitEmpty = game.getPlayer().getPits().stream()
                .allMatch(pit -> game.getGameBoard().get(pit) == 0);

        boolean isOppsitePlayerPitEmpty = game.getPlayer().getOppositePlayer().getPits().stream()
                .allMatch(pit -> game.getGameBoard().get(pit) == 0);

        if(isCurrentPlayerPitEmpty || isOppsitePlayerPitEmpty){
            addAllStonesToKalah(game.getPlayer(), game.getGameBoard());
            addAllStonesToKalah(game.getPlayer().getOppositePlayer(), game.getGameBoard());
            return true;
        }
        return false;
    }

    private void addAllStonesToKalah(Player player, Map<Integer, Integer> board){
        player.getPits().stream().forEach(pit -> {
            int stones = board.get(pit);
            int kalah = player.getKalahId();
            board.put(kalah, board.get(kalah) + stones);
            board.put(pit, 0);
        });
    }
    private void addStonesToPit(int pitId, int amount, Map<Integer, Integer> board) {
        board.replace(pitId, board.get(pitId) + amount);
    }
}
