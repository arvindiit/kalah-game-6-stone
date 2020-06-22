package com.arvind.assignment.kalah.service;

import com.arvind.assignment.kalah.exception.GameNotInProgressException;
import com.arvind.assignment.kalah.domain.Game;
import com.arvind.assignment.kalah.dto.GameDTO;
import com.arvind.assignment.kalah.exception.GameNotFoundException;
import com.arvind.assignment.kalah.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * Author: Arvind Pandey On: 20/06/2020
 * Service class to be call from controller.
 * Acts as layer between controller and facade
 */

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    GameFacade gameFacade;

    @Autowired
    Environment environment;

    /**
     * Creating a new Game
     * @return GameDTO will not contain status(=null)
     */
    public GameDTO createGame(){
        Game game =  gameRepository.save(new Game());
        return game.toNewGameDTO(environment.getProperty("kalah.game.url"));

    }

    /**
     * For making move by the player
     * @return GameDTO will contain status
     */
    public GameDTO makeMove(int gameId, int pitId) {
        Game existingGame = gameRepository.findById(gameId).orElseThrow(() -> new GameNotFoundException("No Game found with id: " + gameId));
        if( existingGame.getStatus().equals(Game.Status.CREATED)){
            existingGame.setStatus(Game.Status.IN_PROGRESS);
        }
        if(!existingGame.getStatus().equals(Game.Status.IN_PROGRESS)){
            throw new GameNotInProgressException("Game is not in progress. Current status of game is: " + existingGame.getStatus().name());
        }
        gameFacade.makeMove(pitId, existingGame);
        Game game = gameRepository.save(existingGame);
        return game.toGameDTO(environment.getProperty("kalah.game.url"));
    }

}
