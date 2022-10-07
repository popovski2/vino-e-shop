package com.graduationproject.vinoeshop.service.impl;

import com.graduationproject.vinoeshop.model.Category;
import com.graduationproject.vinoeshop.model.Type;
import com.graduationproject.vinoeshop.model.Wine;
import com.graduationproject.vinoeshop.model.exceptions.InvalidCategoryIdException;
import com.graduationproject.vinoeshop.model.exceptions.InvalidTypeIdException;
import com.graduationproject.vinoeshop.repository.CategoryRepository;
import com.graduationproject.vinoeshop.repository.TypeRepository;
import com.graduationproject.vinoeshop.service.TypeService;
import com.graduationproject.vinoeshop.service.WineService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;
    private final CategoryRepository categoryRepository;
    private final WineService wineService;

    // I use @Lazy because the application will crash since we use circular dependencies CategoryServiceImpl->TypeServiceImpl->WineServiceImpl
    public TypeServiceImpl(TypeRepository typeRepository, CategoryRepository categoryRepository, @Lazy WineService wineService) {
        this.typeRepository = typeRepository;
        this.categoryRepository = categoryRepository;
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
    public List<Type> listAllTypes() {
        return this.typeRepository.findAll();
    }

    @Override
    public Optional<Type> findById(Long typeId) {
        return Optional.ofNullable(this.typeRepository.findById(typeId).orElseThrow(() -> new InvalidTypeIdException(typeId)));
    }

    @Override
    public List<Type> findAllTypesWithCategoryId(Long categoryId) {
        return this.typeRepository.findAllByCategory_Id(categoryId);
    }

    /**
     *                      *
     *                      *
     *          POST        *
     *                      *
     *                      *
     * **/
    @Override
    public Type create(String name, String description, Long categoryId) {
        if (name==null || name.isEmpty() || description==null || description.isEmpty()) {
            throw new IllegalArgumentException();
        }

        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new InvalidCategoryIdException(categoryId));
        Type type = new Type(name,description,category);
        this.typeRepository.save(type);
        return type;
    }

    /**
     *                      *
     *                      *
     *          PUT         *
     *                      *
     *                      *
     * **/
    @Override
    public Type update(Long typeId, String name, String description, Long categoryId) {

        if (name==null || name.isEmpty() || description==null || description.isEmpty()) {
            throw new IllegalArgumentException();
        }

        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new InvalidCategoryIdException(categoryId));
        Type type = this.typeRepository.findById(typeId).orElseThrow(() -> new InvalidTypeIdException(typeId));
        type.setName(name);
        type.setDescription(description);
        type.setCategory(category);
        this.typeRepository.save(type);
        return type;
    }

    /**
     *                      *
     *                      *
     *        DELETE        *
     *                      *
     *                      *
     * **/
    @Override
    public Type delete(Long typeId) {

        Type typeForDeletion = this.typeRepository.findById(typeId).orElseThrow(() -> new InvalidTypeIdException(typeId));

        //we need to find all the wines of type with typeId and delete  them
        this.wineService.deleteWinesByManufacturer(typeId);

        // we delete the type
        this.typeRepository.delete(typeForDeletion);
        return typeForDeletion;
    }

    @Override
    public void deleteAllTypesOfCategory(Long categoryId) {
        //  firstly, get all the types
        List<Type> types = this.typeRepository.findAll();
        //then filter the types that belong to this category
        types = types.stream()
                .filter(t -> t.getCategory().getId().equals(categoryId)).
                        collect(Collectors.toList());


        // check if there are any wines associated with these types
        types.forEach(t -> this.wineService.deleteWinesByTypeId(t.getId()));

        this.typeRepository.deleteAll(types);
    }
}
