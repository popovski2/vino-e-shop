package com.graduationproject.vinoeshop.model.exceptions;

public class InvalidOrderIdException extends RuntimeException{
    public InvalidOrderIdException(Long id) {
        super(String.format("404, Order with id %d was not found", id));
    }
}
