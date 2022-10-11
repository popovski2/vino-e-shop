package com.graduationproject.vinoeshop.model.exceptions;

public class InvalidUserArgumentsException extends RuntimeException {
    public InvalidUserArgumentsException() {
        super("Invalid username/password.");
    }

}
