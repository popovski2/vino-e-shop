package com.graduationproject.vinoeshop.service.impl;

import com.graduationproject.vinoeshop.model.ShoppingCart;
import com.graduationproject.vinoeshop.model.User;
import com.graduationproject.vinoeshop.model.Wine;
import com.graduationproject.vinoeshop.model.enumerations.ShoppingCartStatus;
import com.graduationproject.vinoeshop.model.exceptions.InvalidShoppingCartIdException;
import com.graduationproject.vinoeshop.model.exceptions.InvalidWineIdException;
import com.graduationproject.vinoeshop.model.exceptions.WineAlreadyInShoppingCartException;
import com.graduationproject.vinoeshop.repository.ShoppingCartRepository;
import com.graduationproject.vinoeshop.repository.UserRepository;
import com.graduationproject.vinoeshop.service.ShoppingCartService;
import com.graduationproject.vinoeshop.service.WineService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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


    /**
     *                      *
     *                      *
     *          GET         *
     *                      *
     *                      *
     * **/
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
        if(!this.shoppingCartRepository.findById(cartId).isPresent()){
            throw new InvalidShoppingCartIdException(cartId);
        }

        List<Wine> wines = this.shoppingCartRepository.findById(cartId).get().getWines();
        double totalPrice = 0;

        totalPrice += wines.stream().mapToDouble(wine -> wine.getPrice() * wine.getQuantity()).sum();
        return totalPrice;
    }


    /**
     *                      *
     *                      *
     *          POST        *
     *                      *
     *                      *
     * **/
    @Override
    public ShoppingCart addWinesToShoppingCart(String username, Long wineId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        Wine wine = this.wineService.findById(wineId).orElseThrow(() -> new InvalidWineIdException(wineId));

        if(shoppingCart.getWines()
        .stream().filter(i-> i.getId().equals(wineId))
        .collect(Collectors.toList()).size() > 0){
            throw new WineAlreadyInShoppingCartException(wineId,username);
        }

        shoppingCart.getWines().add(wine);
        wine.setQuantity(1);
        Double newPrice = this.computeTotalPrice(shoppingCart.getId());
        shoppingCart.setTotalPrice(newPrice);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public void emptyShoppingCart(String username) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        shoppingCart.getWines().clear();
        Double newPrice = this.computeTotalPrice(shoppingCart.getId());
        shoppingCart.setTotalPrice(newPrice);
        this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public void increaseQuantity(String username, Long wineId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        Wine wine = this.wineService.findById(wineId).orElseThrow(() -> new InvalidWineIdException(wineId));
        wine.setQuantity(wine.getQuantity()+1);
        Double newPrice = this.computeTotalPrice(shoppingCart.getId());
        shoppingCart.setTotalPrice(newPrice);
        this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public void decreaseQuantity(String username, Long wineId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        Wine wine = this.wineService.findById(wineId).orElseThrow(() -> new InvalidWineIdException(wineId));
        wine.setQuantity(wine.getQuantity()-1);

        if(wine.getQuantity() == 0)
            shoppingCart.getWines().remove(wine);

        Double newPrice = this.computeTotalPrice(shoppingCart.getId());
        shoppingCart.setTotalPrice(newPrice);
        this.shoppingCartRepository.save(shoppingCart);
    }

    /**
     *                      *
     *                      *
     *         DELETE       *
     *                      *
     *                      *
     * **/
    @Override
    public void deleteFromShoppingCart(String username, Long wineId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        Wine wine = this.wineService.findById(wineId).orElseThrow(() -> new InvalidWineIdException(wineId));
        shoppingCart.getWines().remove(wine);
        this.shoppingCartRepository.save(shoppingCart);
    }



}
