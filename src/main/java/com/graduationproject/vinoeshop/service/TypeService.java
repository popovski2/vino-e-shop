package com.graduationproject.vinoeshop.service;

import com.graduationproject.vinoeshop.model.Type;

import java.util.List;
import java.util.Optional;

public interface TypeService {


    /**
     *                          *
     *                          *
     *      GET FUNCTIONS       *
     *                          *
     *                          *
     **/


    /** LIST ALL TYPES **/
    List<Type> listAllTypes();

    /** FIND TYPE BY ID **/
    Optional<Type> findById(Long typeId);

    /** FIND ALL TYPES THAT BELONG TO ONE CATEGORY**/
    List<Type> findAllTypesWithCategoryId(Long categoryId);



    /**
     *                          *
     *                          *
     *      POST FUNCTIONS      *
     *                          *
     *                          *
     **/
    Type create(String name, String description, Long categoryId);


    /**
     *                          *
     *                          *
     *      PUT FUNCTIONS       *
     *                          *
     *                          *
     **/
    Type update(Long typeId, String name, String description, Long categoryId);


    /**
     *                          *
     *                          *
     *     DELETE FUNCTIONS     *
     *                          *
     *                          *
     **/

    /** DELETE TYPE WITH TYPE ID **/
    Type delete(Long typeId);

    /** DELETE ALL TYPES THAT BELONG TO CATEGORY **/
    void deleteAllTypesOfCategory(Long categoryId);


}
