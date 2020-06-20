package com.arvind.assignment.kalah.repository;

import com.arvind.assignment.kalah.domain.Game;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    @Test
    public void testSaveGame(){
        Game game = gameRepository.save(new Game());

        assertEquals(1, game.getId());
        assertEquals(Game.Status.CREATED, game.getStatus());
        gameRepository.delete(game);
    }
}
