package com.arvind.assignment.kalah.domain;

import com.arvind.assignment.kalah.dto.GameDTO;
import com.arvind.assignment.kalah.utils.Constants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.util.UriTemplate;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
/**
 * Author: Arvind Pandey On: 20/06/2020
 * Entity class for Game
 */
@Entity
@Getter
@Setter
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue
    private int id;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @Enumerated(value = EnumType.STRING)
    private Player player;

    private String winner;

    @ElementCollection
    @MapKeyColumn(name="pitId")
    @Column(name="value")
    private Map<Integer, Integer> gameBoard;
    public Game() {
        this.status = Status.CREATED;
        this.player = Player.FIRST_PLAYER;
        this.gameBoard = createBoard();
    }

    public GameDTO toNewGameDTO(String gameUrl) {
        var url = new UriTemplate(gameUrl).expand(id).toString();
        return new GameDTO(id, url);
    }
    
    public GameDTO toGameDTO(String gameUrl) {
        var status = this.getGameBoard();
        var url = new UriTemplate(gameUrl).expand(id).toString();
        return new GameDTO(id, url, status, winner);
    }
    
    private Map<Integer, Integer> createBoard() {
        Map<Integer, Integer> newBoard = new HashMap<>();
        for (int i = Constants.FIRST_PIT_INDEX; i <= Constants.LAST_PIT_INDEX; i++) {
            int firstPlayedKalahId = Player.FIRST_PLAYER.getKalahId();
            int secondPlayerKalahId = Player.SECOND_PLAYER.getKalahId();
            int stones = (i != firstPlayedKalahId && i != secondPlayerKalahId) ? Constants.PLAYING_STONES_WITH : 0;
            newBoard.put(i, stones);
        }
        return newBoard;
    }

    public enum Status {
        CREATED, IN_PROGRESS, OVER
    }
}
