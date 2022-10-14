package com.graduationproject.vinoeshop.service;

import com.graduationproject.vinoeshop.model.Order;
import com.graduationproject.vinoeshop.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {


    /**
     *                          *
     *                          *
     *      GET FUNCTIONS       *
     *                          *
     *                          *
     **/

    User getUser(String username);


    User login(String username, String password);



    /**
     *                          *
     *                          *
     *      POST FUNCTIONS      *
     *                          *
     *                          *
     **/

    User saveUser(User user);

    User updateUsersOrders(Long userId , Long orderId);

    List<User> getUsers();


    User register(String email, String password, String name, String surname);


    User registerAsManufacturer(String email, String password, String name, String surname);




}
