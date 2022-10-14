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

    List<User> getAllUsers();

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


    User register(String email, String password, String name, String surname,String address);


    User registerAsManufacturer(String email, String password, String name, String surname);

    User registerAsAdministrator(String email, String password, String name, String surname);

    /**
     *                          *
     *                          *
     *      DELETE FUNCTIONS    *
     *                          *
     *                          *
     **/
    User delete(Long userId);


}
