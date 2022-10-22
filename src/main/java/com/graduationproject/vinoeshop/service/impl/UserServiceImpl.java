package com.graduationproject.vinoeshop.service.impl;

import com.graduationproject.vinoeshop.model.Order;
import com.graduationproject.vinoeshop.model.User;
import com.graduationproject.vinoeshop.model.enumerations.Role;
import com.graduationproject.vinoeshop.model.exceptions.InvalidOrderIdException;
import com.graduationproject.vinoeshop.model.exceptions.InvalidUserArgumentsException;
import com.graduationproject.vinoeshop.model.exceptions.InvalidUserIdException;
import com.graduationproject.vinoeshop.repository.OrderRepository;
import com.graduationproject.vinoeshop.repository.ShoppingCartRepository;
import com.graduationproject.vinoeshop.repository.UserRepository;
import com.graduationproject.vinoeshop.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, OrderRepository orderRepository, ShoppingCartRepository shoppingCartRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     *                          *
     *                          *
     *      GET FUNCTIONS       *
     *                          *
     *                          *
     **/

    @Override
    public User getUser(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }



    @Override
    public User login(String username, String password) {
        if (username==null || username.isEmpty() || password==null || password.isEmpty()) {
            throw new InvalidUserArgumentsException();
        }
        return userRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username);
    }

    /**
     *                          *
     *                          *
     *      POST FUNCTIONS      *
     *                          *
     *                          *
     **/

    @Override
    public User saveUser(User user) {
        //log.info("Saving new user {} to the DB", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User updateUsersOrders(Long userId, Long orderId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new InvalidUserIdException(userId));
        Order order = this.orderRepository.findById(orderId).orElseThrow(() -> new InvalidOrderIdException(orderId));
        user.addOrder(order);
        return this.userRepository.save(user);
    }


    @Override
    public User register(String email, String password, String name, String surname, String address) {
        if (email==null || email.isEmpty()  || password==null || password.isEmpty()) {
            throw new InvalidUserArgumentsException();
        }

        String encryptedPassword = this.passwordEncoder.encode(password);

        User user = new User(email, name, surname, encryptedPassword, Role.ROLE_CUSTOMER,  address);
        return userRepository.save(user);
    }

    @Override
    public User registerAsManufacturer(String email, String password, String name, String surname) {
        if (email==null || email.isEmpty()  || password==null || password.isEmpty()) {
            throw new InvalidUserArgumentsException();
        }
        String encryptedPassword = this.passwordEncoder.encode(password);
        User user = new User(email, name, surname, encryptedPassword, Role.ROLE_MANUFACTURER);
        return userRepository.save(user);
    }

    @Override
    public User registerAsAdministrator(String email, String password, String name, String surname) {
        if (email==null || email.isEmpty()  || password==null || password.isEmpty()) {
            throw new InvalidUserArgumentsException();
        }
        String encryptedPassword = this.passwordEncoder.encode(password);
        User user = new User(email, name, surname, encryptedPassword, Role.ROLE_ADMIN);
        return userRepository.save(user);
    }

    /**
     *                          *
     *                          *
     *      DELETE FUNCTIONS    *
     *                          *
     *                          *
     **/
    @Override
    public User delete(Long userId) {
        User userForDeletion = this.userRepository.findById(userId).orElseThrow(() -> new InvalidUserIdException(userId));

        //find his orders first and delete them
        this.orderRepository.deleteAllByShoppingCartUser(userForDeletion);
        //find his shopping carts and  delete them too
        this.shoppingCartRepository.deleteAllByUser(userForDeletion);

        this.userRepository.delete(userForDeletion);
        return userForDeletion;
    }


}
