package com.graduationproject.vinoeshop.model;

import lombok.Data;

import javax.persistence.*;

@Entity
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "fk_category")
    private Category category;


    /** CONSTRUCTORS **/
    public Type(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public Type() {
    }

    /** GETTERS **/
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    /** SETTERS **/

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
