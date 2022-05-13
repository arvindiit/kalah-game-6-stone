package com.arvind.assignment.kalah.service;

import com.arvind.assignment.kalah.exception.GameNotInProgressException;
import com.arvind.assignment.kalah.domain.Game;
import com.arvind.assignment.kalah.dto.GameDTO;
import com.arvind.assignment.kalah.exception.GameNotFoundException;
import com.arvind.assignment.kalah.repository.GameRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * Author: Arvind Pandey
 * Service class to be call from controller.
 * Acts as layer between controller and facade
 */

@Service
@AllArgsConstructor
public class GameService {
    
    private final GameRepository gameRepository;
    private final GameFacade gameFacade;
    private final Environment environment;

  
    public GameDTO createGame() {
        Game game =  gameRepository.save(new Game());
        return game.toNewGameDTO(environment.getProperty("kalah.game.url"));

    }
    
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
