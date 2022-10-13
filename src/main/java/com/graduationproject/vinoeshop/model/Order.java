package com.graduationproject.vinoeshop.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "order_table")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

/*
    @ManyToOne
    private User user;

    @ManyToMany
    private List<Wine> wines;

    private Double totalPrice;

    private LocalDateTime date;*/

    @OneToOne
    private ShoppingCart shoppingCart;

    /** CONSTRUCTORS **/

    public Order() {
    }

    public Order(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
    /*  public Order(User user, List<Wine> wines, Double totalPrice, LocalDateTime date) {
        this.user = user;
        this.wines = wines;
        this.totalPrice = totalPrice;
        this.date = date;
    }*/

    /** GETTERS **/
    public Long getId() {
        return id;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    /** SETTERS **/

    public void setId(Long id) {
        this.id = id;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}
