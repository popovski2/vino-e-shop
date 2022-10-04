package com.graduationproject.vinoeshop.service.impl;

import com.graduationproject.vinoeshop.model.Category;
import com.graduationproject.vinoeshop.model.exceptions.InvalidCategoryIdException;
import com.graduationproject.vinoeshop.repository.CategoryRepository;
import com.graduationproject.vinoeshop.service.CategoryService;
import com.graduationproject.vinoeshop.service.TypeService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final TypeService typeService;
    public CategoryServiceImpl(CategoryRepository categoryRepository, TypeService typeService) {
        this.categoryRepository = categoryRepository;
        this.typeService = typeService;
    }


    /**
     *                      *
     *                      *
     *          GET         *
     *                      *
     *                      *
     * **/

    @Override
    public List<Category> listAllCategories() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(Long categoryId) {
        return Optional.ofNullable(this.categoryRepository.findById(categoryId).orElseThrow(() -> new InvalidCategoryIdException(categoryId)));
    }

    @Override
    public Category findByName(String categoryName) {
        return this.categoryRepository.findCategoryByName(categoryName);
    }


    /**
     *                      *
     *                      *
     *          POST        *
     *                      *
     *                      *
     * **/

    @Override
    public Category create(String name) {
        //checking if the arguments are passed correctly
        if (name==null || name.isEmpty()) {
            throw new IllegalArgumentException();
        }

        Category category = new Category(name);
        this.categoryRepository.save(category);
        return category;
    }


    /**
     *                      *
     *                      *
     *          PUT         *
     *                      *
     *                      *
     * **/

    @Override
    public Category update(Long categoryId, String name) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new InvalidCategoryIdException(categoryId));
        category.setName(name);
        this.categoryRepository.save(category);
        return category;
    }

    /**
     *                      *
     *                      *
     *        DELETE        *
     *                      *
     *                      *
     * **/
    @Override
    public Category delete(Long categoryId) {

        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new InvalidCategoryIdException(categoryId));

        // when deleting Category we need to delete all the associated types to that category
        this.typeService.deleteAllTypesOfCategory(categoryId);
        // then we are deleting the category
        this.categoryRepository.delete(category);
        return category;
    }
}
