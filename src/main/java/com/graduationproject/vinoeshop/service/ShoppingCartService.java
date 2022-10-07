package com.graduationproject.vinoeshop.service;


import com.graduationproject.vinoeshop.model.ShoppingCart;
import com.graduationproject.vinoeshop.model.Wine;

import java.util.List;

public interface ShoppingCartService {


    /**
     *                          *
     *                          *
     *      GET FUNCTIONS       *
     *                          *
     *                          *
     **/

    List<Wine> listAllWinesInShoppingCart(Long shoppingCartId);

    ShoppingCart getActiveShoppingCart(String username);

    Double computeTotalPrice(Long cartId);

    /**
     *                          *
     *                          *
     *      POST FUNCTIONS      *
     *                          *
     *                          *
     **/

    ShoppingCart addWinesToShoppingCart(String username, Long wineId);

    void emptyShoppingCart(String username);

    void increaseQuantity(String username, Long wineId);

    void decreaseQuantity(String username, Long wineId);

    /**
     *                          *
     *                          *
     *     DELETE FUNCTIONS     *
     *                          *
     *                          *
     **/

    void deleteFromShoppingCart(String username, Long wineId);


}
