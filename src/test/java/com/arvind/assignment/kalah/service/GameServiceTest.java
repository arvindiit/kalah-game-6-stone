package com.arvind.assignment.kalah.service;

import com.arvind.assignment.kalah.domain.Game;
import com.arvind.assignment.kalah.dto.GameDTO;
import com.arvind.assignment.kalah.repository.GameRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {

    private static final String URL = "http://localhost:8080/games/1";

    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameFacade gameFacade;

    @Mock
    Environment environment;

    @InjectMocks
    private GameService gameService;

    @Test
    public void testCreateGame() {
        Game game = new Game();
        game.setId(1);
        when(gameRepository.save(any(Game.class))).thenReturn(game);
        when(environment.getProperty("kalah.game.url")).thenReturn(URL);
        GameDTO gameDTO = gameService.createGame();
        assertEquals(1, gameDTO.getId());
        assertEquals(URL, gameDTO.getUrl());
    }

    @Test
    public void testMakeMove() {
        Game game = new Game();
        game.setId(1);
        when(gameRepository.findById(1)).thenReturn(Optional.of(game));
        when(gameRepository.save(game)).thenReturn(game);
        when(environment.getProperty("kalah.game.url")).thenReturn(URL);
        GameDTO gameDTO = gameService.makeMove(1, 5);

        verify(gameFacade).makeMove(5, game);
        assertEquals(1, gameDTO.getId());
        assertEquals(URL, gameDTO.getUrl());
    }
}
