package com.arvind.assignment.kalah.exception;

public class GameNotInProgressException extends RuntimeException{

    public GameNotInProgressException(String message) {
        super(message);
    }
}
