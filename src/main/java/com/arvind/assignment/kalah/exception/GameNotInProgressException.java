package com.arvind.assignment.kalah.exception;

public class GameNotInProgressException extends RuntimeException{

    public GameNotInProgressException() {
        super();
    }

    public GameNotInProgressException(String message) {
        super(message);
    }

    public GameNotInProgressException(String message, Throwable cause) {
        super(message, cause);
    }
}
