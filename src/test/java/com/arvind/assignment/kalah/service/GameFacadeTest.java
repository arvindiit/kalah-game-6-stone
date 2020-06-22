package com.arvind.assignment.kalah.service;

import com.arvind.assignment.kalah.domain.Game;
import com.arvind.assignment.kalah.exception.InvalidPitException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Map;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class GameFacadeTest {

    @InjectMocks
    private GameFacade gameFacade;

    private Game game;

    @Before
    public void init(){
        game = new Game();
    }

    @Test
    public void testMakeMove(){
        int pitId = 1;
        Map<Integer, Integer> boardBeforeMove = game.getGameBoard();
        gameFacade.makeMove(pitId, game);

        IntStream.range(pitId+1, pitId+boardBeforeMove.get(pitId)+1).forEach(pit -> {
            assertEquals(boardBeforeMove.get(pit) + 1, game.getGameBoard().get(pit).intValue());
        });
    }

    @Test
    public void testNotValidMove(){
        int pitId = 8;
        Map<Integer, Integer> boardBeforeMove = game.getGameBoard();
        assertThrows(InvalidPitException.class,
                () -> gameFacade.makeMove(pitId, game));

        assertThrows(InvalidPitException.class,
                () -> gameFacade.makeMove(7, game));
    }

}
