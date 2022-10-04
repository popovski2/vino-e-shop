package com.graduationproject.vinoeshop.service.impl;

import com.graduationproject.vinoeshop.model.Category;
import com.graduationproject.vinoeshop.model.Manufacturer;
import com.graduationproject.vinoeshop.model.Type;
import com.graduationproject.vinoeshop.model.Wine;
import com.graduationproject.vinoeshop.model.exceptions.InvalidManufacturerIdException;
import com.graduationproject.vinoeshop.model.exceptions.InvalidWineIdException;
import com.graduationproject.vinoeshop.repository.ManufacturerRepository;
import com.graduationproject.vinoeshop.repository.WineRepository;
import com.graduationproject.vinoeshop.service.WineService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WineServiceImpl implements WineService {

    private WineRepository wineRepository;
    private ManufacturerRepository manufacturerRepository;

    /**     DEPENDENCY INJECTION FOR THE REPOSITORIES      **/
    public WineServiceImpl(WineRepository wineRepository, ManufacturerRepository manufacturerRepository) {
            this.wineRepository = wineRepository;
            this.manufacturerRepository = manufacturerRepository;
    }

    /**
     *                      *
     *          GET         *
     *                      *
     *    **/
    @Override
    public List<Wine> listAllWines() {
        return this.wineRepository.findAll();
    }

    @Override
    public Optional<Wine> findById(Long id) {
        return Optional.ofNullable(this.wineRepository.findById(id).orElseThrow(() -> new InvalidWineIdException(id)));
    }

    @Override
    public List<Wine> listWinesByCategoryName(String name) {
        return this.wineRepository.findAllByCategoryName(name);
    }

    /**
     *                      *
     *          POST        *
     *                      *
     *    **/

    @Override
    public Wine create(String name, Double price, Integer quantity, String imageUrl, Long categoryId, Long manufacturerId, Long typeId) {
        Category category;
        Manufacturer manufacturer;
        Type type;
       // create new wine
      //  Wine wine = new Wine(name,price,quantity,imageUrl)

    }


    /**
     *                      *
     *          PUT         *
     *                      *
     *    **/
    @Override
    public Wine update(Long id, String name, Double price, Integer quantity, String imageUrl, Long categoryId, Long manufacturerId, Long typeId) {
        return null;
    }


    /**
     *                      *
     *        DELETE        *
     *                      *
     *    **/
    @Override
    public Wine delete(Long wineId) {
        Wine wineForDeletion = this.wineRepository.findById(wineId).orElseThrow(() -> new InvalidWineIdException(wineId));
        this.wineRepository.delete(wineForDeletion);
        return wineForDeletion;
    }

    @Override
    public void deleteByTypeId(Long typeId) {

    }

    @Override
    public void deleteWinesByManufacturer(Long manufacturerId) {

        Manufacturer manufacturer = this.manufacturerRepository.findById(manufacturerId).orElseThrow(() -> new InvalidManufacturerIdException(manufacturerId));

        //  firstly, get all wines
        List<Wine> wines = this.wineRepository.findAll();
        //then filter the wines made by this manufacturer
        wines = wines.stream()
                .filter(w -> w.getManufacturer().getId().equals(manufacturerId)).
                        collect(Collectors.toList());
        this.wineRepository.deleteAll(wines);
    }
}
