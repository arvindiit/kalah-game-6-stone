package com.arvind.assignment.kalah.exception;

import java.util.List;

public class InvalidPitException extends RuntimeException {

    List<Integer> ownPits;

    public InvalidPitException(String message) {
        super(message);
    }

    public InvalidPitException(String message, List<Integer> pits) {
        super(message);
        this.ownPits = pits;
    }

    public List<Integer> getOwnPits(){
        return this.ownPits;
    }
}
