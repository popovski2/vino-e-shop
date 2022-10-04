package com.graduationproject.vinoeshop.model.exceptions;

public class InvalidTypeIdException extends RuntimeException {
    public InvalidTypeIdException(Long typeId) {
        super(String.format("404, Type with id %d was not found", typeId));
    }
}
