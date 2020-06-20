package com.arvind.assignment.kalah.controller;

import com.arvind.assignment.kalah.service.GameService;
import com.arvind.assignment.kalah.dto.GameDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Author: Arvind Pandey
 * On: 20/06/2020
 * Controller class to handle the http request
 */
@RestController
@RequestMapping("/games")
public class GameController {


    @Autowired
    GameService gameService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameDTO createNewGame() {
        return gameService.createGame();
    }

    @PutMapping("/{gameId}/pits/{pitId}")
    public GameDTO makeMove(@PathVariable int gameId, @PathVariable int pitId) {
        return gameService.makeMove(gameId, pitId);
    }
}
