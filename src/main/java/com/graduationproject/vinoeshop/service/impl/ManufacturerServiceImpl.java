package com.graduationproject.vinoeshop.service.impl;

import com.graduationproject.vinoeshop.model.Manufacturer;
import com.graduationproject.vinoeshop.model.exceptions.InvalidManufacturerIdException;
import com.graduationproject.vinoeshop.repository.ManufacturerRepository;
import com.graduationproject.vinoeshop.service.ManufacturerService;
import com.graduationproject.vinoeshop.service.WineService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    private WineService wineService;
    private ManufacturerRepository manufacturerRepository;

    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository, WineService wineService) {
        this.manufacturerRepository = manufacturerRepository;
        this.wineService = wineService;
    }

    /**
     *                      *
     *                      *
     *          GET         *
     *                      *
     *                      *
     * **/

    @Override
    public List<Manufacturer> listAllManufacturers() {
        return this.manufacturerRepository.findAll();
    }

    @Override
    public Optional<Manufacturer> findById(Long manufacturerId) {
        return Optional.ofNullable(this.manufacturerRepository.findById(manufacturerId).orElseThrow(() -> new InvalidManufacturerIdException(manufacturerId)));
    }


    /**
     *                      *
     *                      *
     *          POST        *
     *                      *
     *                      *
     * **/

    @Override
    public Manufacturer create(String name, String address) {
        //checking if the arguments are passed correctly
        if (name==null || name.isEmpty() || address==null || address.isEmpty()) {
            throw new IllegalArgumentException();
        }

        Manufacturer manufacturer = new Manufacturer(name,address);
        this.manufacturerRepository.save(manufacturer);
        return manufacturer;
    }



    /**
     *                      *
     *                      *
     *          PUT         *
     *                      *
     *                      *
     * **/

    @Override
    public Manufacturer update(Long manufacturerId, String name, String address) {
        Manufacturer manufacturer = this.manufacturerRepository.findById(manufacturerId).orElseThrow(() -> new InvalidManufacturerIdException(manufacturerId));
        manufacturer.setName(name);
        manufacturer.setAddress(address);
        return this.manufacturerRepository.save(manufacturer);
    }


    /**
     *                      *
     *                      *
     *        DELETE        *
     *                      *
     *                      *
     * **/

    @Override
    public Manufacturer delete(Long manufacturerId) {

        // first of all, delete all the wines from this manufacturer
        this.wineService.deleteWinesByManufacturer(manufacturerId);

        // secondly, delete the manufacturer
        Manufacturer manufacturerForDeletion = this.manufacturerRepository.findById(manufacturerId).orElseThrow(() -> new InvalidManufacturerIdException(manufacturerId));
        this.manufacturerRepository.delete(manufacturerForDeletion);
        return manufacturerForDeletion;
    }
}
