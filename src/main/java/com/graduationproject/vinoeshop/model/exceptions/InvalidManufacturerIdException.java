package com.graduationproject.vinoeshop.model.exceptions;

public class InvalidManufacturerIdException extends RuntimeException {
    public InvalidManufacturerIdException(Long id) {
        super(String.format("404, Manufacturer with id %d was not found", id));
    }
}
