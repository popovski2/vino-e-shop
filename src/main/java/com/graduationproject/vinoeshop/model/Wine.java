package com.graduationproject.vinoeshop.model;

import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;

@Entity
public class Wine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Double price;

    private Integer quantity;

    private String imageUrl;


    @ManyToOne
    private Category category;

    @ManyToOne
    private Manufacturer manufacturer;

    @OneToOne
    private Type type;


    /** CONSTRUCTORS **/

    public Wine(String name, Double price, Integer quantity, String imageUrl, Category category, Manufacturer manufacturer, Type type) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
        this.category = category;
        this.manufacturer = manufacturer;
        this.type = type;
    }

    /** GETTERS **/
    public Long getId() {
        return id;
    }

    public Double getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public Type getType() {
        return type;
    }

    /** SETTERS **/

    public void setId(Long id) {
        this.id = id;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
