package com.graduationproject.vinoeshop.model;

import com.graduationproject.vinoeshop.model.enumerations.ShoppingCartStatus;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateCreated;

    @ManyToOne
    private User user;

    @ManyToMany
    private List<Wine> wines;

    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    private ShoppingCartStatus status;

    //ne znam dali se koristit ova ili to gore dateCreated
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    /** CONSTRUCTORS **/
    public ShoppingCart(User user) {
        this.user = user;
        this.wines = new ArrayList<>();
        this.status = ShoppingCartStatus.CREATED;
    }

    public ShoppingCart()
    {
        this.id = (long) (Math.random()*1000);
    }


    /** GETTERS **/
    public Long getId() {
        return id;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public User getUser() {
        return user;
    }

    public List<Wine> getWines() {
        return wines;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public ShoppingCartStatus getStatus() {
        return status;
    }

    public Date getDate() {
        return date;
    }
    /** SETTERS **/

    public void setId(Long id) {
        this.id = id;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setWines(List<Wine> wines) {
        this.wines = wines;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setStatus(ShoppingCartStatus status) {
        this.status = status;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
