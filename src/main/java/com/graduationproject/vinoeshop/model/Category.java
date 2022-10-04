package com.graduationproject.vinoeshop.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Category {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany
    private List<Type> types;



    /** CONSTRUCTORS **/
    public Category(String name, List<Type> types) {
        this.name = name;
        this.types = types;
    }

    public Category(String name) {
        this.name = name;
    }

    /** GETTERS **/
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Type> getTypes() {
        return types;
    }

    /** SETTERS **/

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }
}
