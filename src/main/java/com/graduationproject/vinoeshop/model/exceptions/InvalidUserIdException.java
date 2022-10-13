package com.graduationproject.vinoeshop.model.exceptions;

public class InvalidUserIdException extends RuntimeException {
    public InvalidUserIdException(Long userId) {
        super(String.format("404, User with id %d was not found", userId));
    }
}
