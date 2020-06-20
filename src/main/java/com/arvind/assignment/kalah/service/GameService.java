package com.arvind.assignment.kalah.service;

import com.arvind.assignment.kalah.exception.GameNotInProgressException;
import com.arvind.assignment.kalah.domain.Game;
import com.arvind.assignment.kalah.dto.GameDTO;
import com.arvind.assignment.kalah.exception.GameNotFoundException;
import com.arvind.assignment.kalah.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    GameFacade gameFacade;

    @Autowired
    Environment environment;

    public GameDTO createGame(){
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
