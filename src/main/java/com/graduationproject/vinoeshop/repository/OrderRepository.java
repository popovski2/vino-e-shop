package com.graduationproject.vinoeshop.repository;

import com.graduationproject.vinoeshop.model.Order;
import com.graduationproject.vinoeshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByShoppingCartUser(User user);

    void deleteAllByShoppingCartUser(User user);


}
