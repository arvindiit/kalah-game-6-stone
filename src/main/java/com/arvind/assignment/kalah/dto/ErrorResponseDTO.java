package com.arvind.assignment.kalah.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Created by Arvind Pandey on 20/06/2020.
 * Class for retuning Error response.
 */
@Getter
@Setter
public class ErrorResponseDTO {
    private String message;

    private HttpStatus httpStatus;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Integer> ownPits;

    public ErrorResponseDTO(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public ErrorResponseDTO(String message, List<Integer> ownPits, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.ownPits = ownPits;
    }
}
