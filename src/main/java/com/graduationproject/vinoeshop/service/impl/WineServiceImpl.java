package com.graduationproject.vinoeshop.service.impl;

import com.graduationproject.vinoeshop.model.Category;
import com.graduationproject.vinoeshop.model.Manufacturer;
import com.graduationproject.vinoeshop.model.Type;
import com.graduationproject.vinoeshop.model.Wine;
import com.graduationproject.vinoeshop.model.exceptions.InvalidCategoryIdException;
import com.graduationproject.vinoeshop.model.exceptions.InvalidManufacturerIdException;
import com.graduationproject.vinoeshop.model.exceptions.InvalidTypeIdException;
import com.graduationproject.vinoeshop.model.exceptions.InvalidWineIdException;
import com.graduationproject.vinoeshop.repository.CategoryRepository;
import com.graduationproject.vinoeshop.repository.ManufacturerRepository;
import com.graduationproject.vinoeshop.repository.TypeRepository;
import com.graduationproject.vinoeshop.repository.WineRepository;
import com.graduationproject.vinoeshop.service.CategoryService;
import com.graduationproject.vinoeshop.service.WineService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WineServiceImpl implements WineService {

    private final WineRepository wineRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final TypeRepository typeRepository;
    private final CategoryRepository categoryRepository;

    private final CategoryService categoryService;


    /**     DEPENDENCY INJECTION FOR THE REPOSITORIES      **/
    public WineServiceImpl(WineRepository wineRepository, ManufacturerRepository manufacturerRepository, TypeRepository typeRepository, CategoryRepository categoryRepository, CategoryService categoryService) {
            this.wineRepository = wineRepository;
            this.manufacturerRepository = manufacturerRepository;
        this.typeRepository = typeRepository;
        this.categoryRepository = categoryRepository;

        this.categoryService = categoryService;
    }

    /**
     *                      *
     *          GET         *
     *                      *
     *                      **/
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

    @Override
    public List<Wine> listWinesByTypeId(Long typeId) {
        return this.wineRepository.findAllByType_Id(typeId);
    }

    /**
     *                      *
     *          POST        *
     *                      *
     *                      **/

    @Override
    public Wine create(String name, Double price, Integer quantity, String imageUrl, Long categoryId, Long manufacturerId, Long typeId) {

        Type type = this.typeRepository.findById(typeId).orElseThrow(() -> new InvalidTypeIdException(typeId));
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new InvalidCategoryIdException(categoryId));
        Manufacturer manufacturer = this.manufacturerRepository.findById(manufacturerId).orElseThrow(() -> new InvalidManufacturerIdException(manufacturerId));

        // create new wine
       Wine wine = new Wine(name,price,quantity,imageUrl,category,manufacturer,type);
       this.wineRepository.save(wine);
       return wine;

    }


    /**
     *                      *
     *          PUT         *
     *                      *
     *                      **/
    @Override
    public Wine update(Long wineId, String name, Double price, Integer quantity, String imageUrl, Long categoryId, Long manufacturerId, Long typeId) {

        Wine wine = this.wineRepository.findById(wineId).orElseThrow(() -> new InvalidWineIdException(wineId));

        Type type = this.typeRepository.findById(typeId).orElseThrow(() -> new InvalidTypeIdException(typeId));
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new InvalidCategoryIdException(categoryId));
        Manufacturer manufacturer = this.manufacturerRepository.findById(manufacturerId).orElseThrow(() -> new InvalidManufacturerIdException(manufacturerId));

        // update the wine
        wine.setName(name);
        wine.setPrice(price);
        wine.setQuantity(quantity);
        wine.setImageUrl(imageUrl);
        wine.setCategory(category);
        wine.setManufacturer(manufacturer);
        wine.setType(type);
        this.wineRepository.save(wine);
        return wine;
    }


    /**
     *                      *
     *        DELETE        *
     *                      *
     *                      **/
    @Override
    public Wine delete(Long wineId) {
        Wine wineForDeletion = this.wineRepository.findById(wineId).orElseThrow(() -> new InvalidWineIdException(wineId));
        this.wineRepository.delete(wineForDeletion);
        return wineForDeletion;
    }

    @Override
    public void deleteWinesByTypeId(Long typeId) {

        // find all the wines with typeId
        List<Wine> wines = this.wineRepository.findAll();
        wines = wines.stream()
                .filter(w -> w.getType().getId().equals(typeId)).
                        collect(Collectors.toList());
        // delete them
        this.wineRepository.deleteAll(wines);
    }

    @Override
    public void deleteWinesByManufacturer(Long manufacturerId) {


        //  firstly, get all wines
        List<Wine> wines = this.wineRepository.findAll();
        //then filter the wines made by this manufacturer
        wines = wines.stream()
                .filter(w -> w.getManufacturer().getId().equals(manufacturerId)).
                        collect(Collectors.toList());
        this.wineRepository.deleteAll(wines);
    }
}
