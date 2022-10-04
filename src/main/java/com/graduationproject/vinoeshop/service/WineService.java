package com.graduationproject.vinoeshop.service;

import com.graduationproject.vinoeshop.model.Wine;

import java.util.List;
import java.util.Optional;

public interface WineService {


    /**
     *                          *
     *                          *
     *      GET FUNCTIONS       *
     *                          *
     *                          *
     **/


    /** LIST ALL WINES **/
    List<Wine> listAllWines();

    /** FIND WINE BY ID **/
    Optional<Wine> findById(Long id);

    /** LIST WINES BY CATEGORY NAME **/
    List<Wine> listWinesByCategoryName(String categoryName);

    /** LIST WINES BY TYPE ID **/
    List<Wine> listWinesByTypeId(Long typeId);




    /**
     *                          *
     *                          *
     *      POST FUNCTIONS      *
     *                          *
     *                          *
     **/
    Wine create(String name, Double price, Integer quantity, String imageUrl, Long categoryId, Long manufacturerId, Long typeId);


    /**
     *                          *
     *                          *
     *      PUT FUNCTIONS       *
     *                          *
     *                          *
     **/
    Wine update(Long wineId, String name, Double price, Integer quantity, String imageUrl, Long categoryId, Long manufacturerId, Long typeId);


    /**
     *                          *
     *                          *
     *     DELETE FUNCTIONS     *
     *                          *
     *                          *
     **/

    /** DELETE WINE BY WINE ID **/
    Wine delete(Long id);

    /** DELETE ALL WINES WITH TYPE ID **/
    void deleteWinesByTypeId(Long typeId);

    /** DELETE ALL WINES FROM MANUFACTURER WITH MANUFACTURER ID **/
    void deleteWinesByManufacturer(Long manufacturerId);


}
