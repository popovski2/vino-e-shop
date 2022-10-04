package com.graduationproject.vinoeshop.service;

import com.graduationproject.vinoeshop.model.Manufacturer;
import com.graduationproject.vinoeshop.model.Wine;

import java.util.List;
import java.util.Optional;

public interface ManufacturerService {


    /**
     *                          *
     *                          *
     *      GET FUNCTIONS       *
     *                          *
     *                          *
     **/


    /** LIST ALL MANUFACTURERS **/
    List<Manufacturer> listAllManufacturers();

    /** FIND MANUFACTURER BY ID **/
    Optional<Manufacturer> findById(Long manufacturerId);



    /**
     *                          *
     *                          *
     *      POST FUNCTIONS      *
     *                          *
     *                          *
     **/
    Manufacturer create(String name,  String address);


    /**
     *                          *
     *                          *
     *      PUT FUNCTIONS       *
     *                          *
     *                          *
     **/
    Manufacturer update(Long id, String name, String address);


    /**
     *                          *
     *                          *
     *     DELETE FUNCTIONS     *
     *                          *
     *                          *
     **/

    /** DELETE MANUFACTURER WITH MANUFACTURER ID **/
    Manufacturer delete(Long manufacturerId);



}
