package com.arvind.assignment.kalah.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * This is class for retuning the response.
 * Created by Arvind Pandey on 20/06/2020.
 */
@Getter
@Setter
public class GameDTO {
    private int id;
    private String url;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String gameStatus;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String winner;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<Integer, Integer> status;

    public GameDTO(int id, String url, Map<Integer, Integer> status, String winner) {
        this.id = id;
        this.url = url;
        this.status = status;
        this.winner = winner;
    }

    public GameDTO(int id, String url) {
        this.id = id;
        this.url = url;
    }

    public GameDTO() {
    }
}
