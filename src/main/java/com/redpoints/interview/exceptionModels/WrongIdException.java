package com.redpoints.interview.exceptionModels;

public class WrongIdException extends Exception{
    public WrongIdException(Long id) {
        super("Movie not found with ID: " + id);
    }
}
