package com.graduationproject.vinoeshop.model.exceptions;

public class WineAlreadyInShoppingCartException extends RuntimeException {
    public WineAlreadyInShoppingCartException(Long wineId, String username){
        super(String.format("Wine with id %d already exists in %s's shopping cart", wineId, username));
    }
}
