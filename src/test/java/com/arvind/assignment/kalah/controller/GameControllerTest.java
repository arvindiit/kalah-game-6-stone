package com.arvind.assignment.kalah.controller;


import com.arvind.assignment.kalah.dto.GameDTO;
import com.arvind.assignment.kalah.service.GameService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * test for {@link GameController}
 * <p>
 * Author: Arvind Pandey
 * On: 20/06/2020
 */

@RunWith(SpringRunner.class)
@WebMvcTest
public class GameControllerTest {

    private static final String URL = "http://localhost:8080/games/1";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private GameService mockGameService;


    @Test
    public void createGame() throws Exception {
        when(mockGameService.createGame()).thenReturn(new GameDTO(1, URL));
        mockMvc.perform(post("/games")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(notNullValue()))
                .andExpect(jsonPath("$.url").value(notNullValue()));
    }


    @Test
    public void validPitMove() throws Exception {
        GameDTO game = new GameDTO(1, URL);
        when(mockGameService.makeMove(1,5)).thenReturn(new GameDTO(1, URL));
        mockMvc.perform(put("/games/1/pits/5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.url").value(game.getUrl()));
    }
}
