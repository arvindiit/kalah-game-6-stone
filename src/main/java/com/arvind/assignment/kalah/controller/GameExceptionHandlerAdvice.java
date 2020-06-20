package com.arvind.assignment.kalah.controller;

import com.arvind.assignment.kalah.dto.ErrorResponseDTO;
import com.arvind.assignment.kalah.exception.GameNotFoundException;
import com.arvind.assignment.kalah.exception.GameNotInProgressException;
import com.arvind.assignment.kalah.exception.InvalidPitException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Author: Arvind Pandey
 * On: 20/06/2020
 * Exception handler advice class
 */
@RestControllerAdvice
public class GameExceptionHandlerAdvice {

    @ExceptionHandler(GameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDTO handleNotFound(Exception ex) {
        return new ErrorResponseDTO(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GameNotInProgressException.class)
    @ResponseStatus(HttpStatus.GONE)
    public ErrorResponseDTO handleGone(Exception ex) {
        return new ErrorResponseDTO(ex.getMessage(), HttpStatus.GONE);
    }

    @ExceptionHandler(InvalidPitException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handleBadRequest(InvalidPitException ex) {
        return new ErrorResponseDTO(ex.getMessage(), ex.getOwnPits(), HttpStatus.BAD_REQUEST);
    }

}
