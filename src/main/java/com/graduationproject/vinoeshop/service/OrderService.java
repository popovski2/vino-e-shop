package com.graduationproject.vinoeshop.service;

import com.graduationproject.vinoeshop.model.Order;
import com.graduationproject.vinoeshop.model.ShoppingCart;
import com.graduationproject.vinoeshop.model.User;
import com.graduationproject.vinoeshop.model.Wine;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    void stripeCheckout();

    void sendMailToAdmin(ShoppingCart shoppingCart);

    void sendMailToCustomer(ShoppingCart shoppingCart);

   // Order create(Long userId, List<Long> winesId, Double totalPrice, LocalDateTime date);
    Order create(Long shoppingCartId);


}
