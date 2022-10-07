package com.graduationproject.vinoeshop.model.exceptions;

public class InvalidShoppingCartIdException extends RuntimeException {
    public InvalidShoppingCartIdException(Long shoppingCartId) {
        super(String.format("404, Shopping cart with id %d was not found", shoppingCartId));
    }
}
