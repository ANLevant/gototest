package com.test.gototechtest.error;

public class EntityDoesntExistException extends Exception {
    public EntityDoesntExistException(String message) {
        super(message);
    }
}
