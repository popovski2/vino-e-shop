package com.graduationproject.vinoeshop.service;

import com.graduationproject.vinoeshop.model.Category;
import com.graduationproject.vinoeshop.model.Type;

import java.util.List;
import java.util.Optional;

public interface CategoryService {


    /**
     *                          *
     *                          *
     *      GET FUNCTIONS       *
     *                          *
     *                          *
     **/


    /** LIST ALL CATEGORIES **/
    List<Category> listAllCategories();

    /** FIND CATEGORY BY ID **/
    Optional<Category> findById(Long categoryId);



    /**
     *                          *
     *                          *
     *      POST FUNCTIONS      *
     *                          *
     *                          *
     **/
    Category create(String name);


    /**
     *                          *
     *                          *
     *      PUT FUNCTIONS       *
     *                          *
     *                          *
     **/
    Category update(Long categoryId, String name, List<Type> types);


    /**
     *                          *
     *                          *
     *     DELETE FUNCTIONS     *
     *                          *
     *                          *
     **/

    /** DELETE CATEGORY WITH CATEGORY ID **/
    Category delete(Long categoryId);


}
