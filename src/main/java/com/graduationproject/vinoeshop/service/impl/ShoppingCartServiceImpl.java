package com.graduationproject.vinoeshop.service.impl;

import com.graduationproject.vinoeshop.model.ShoppingCart;
import com.graduationproject.vinoeshop.model.User;
import com.graduationproject.vinoeshop.model.Wine;
import com.graduationproject.vinoeshop.model.enumerations.ShoppingCartStatus;
import com.graduationproject.vinoeshop.model.exceptions.InvalidShoppingCartIdException;
import com.graduationproject.vinoeshop.repository.ShoppingCartRepository;
import com.graduationproject.vinoeshop.repository.UserRepository;
import com.graduationproject.vinoeshop.service.ShoppingCartService;
import com.graduationproject.vinoeshop.service.WineService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final WineService wineService;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, UserRepository userRepository, WineService wineService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.wineService = wineService;
    }


    @Override
    public List<Wine> listAllWinesInShoppingCart(Long shoppingCartId) {
        ShoppingCart shoppingCart = this.shoppingCartRepository.findById(shoppingCartId).orElseThrow(() -> new InvalidShoppingCartIdException(shoppingCartId));
        return shoppingCart.getWines();
    }

    @Override
    public ShoppingCart getActiveShoppingCart(String username) {
        User user = this.userRepository.findByUsername(username);

        return this.shoppingCartRepository
                .findByUserAndStatus(user, ShoppingCartStatus.CREATED)
                .orElseGet(() -> {
                    ShoppingCart cart = new ShoppingCart(user);
                    return this.shoppingCartRepository.save(cart);
                });
    }

    @Override
    public Double computeTotalPrice(Long cartId) {
        return null;
    }

    @Override
    public ShoppingCart addWinesToShoppingCart(String username, Long wineId) {
        return null;
    }

    @Override
    public void emptyShoppingCart(String username) {

    }

    @Override
    public void increaseQuantity(String username, Long wineId) {

    }

    @Override
    public void decreaseQuantity(String username, Long wineId) {

    }

    @Override
    public void deleteFromShoppingCart(String username, Long wineId) {

    }
}
