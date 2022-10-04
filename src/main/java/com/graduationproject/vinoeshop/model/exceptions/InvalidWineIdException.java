package com.graduationproject.vinoeshop.model.exceptions;

public class InvalidWineIdException extends RuntimeException {
    public InvalidWineIdException(Long id) {
        super(String.format("404, Wine with id %d was not found", id));
    }
}
