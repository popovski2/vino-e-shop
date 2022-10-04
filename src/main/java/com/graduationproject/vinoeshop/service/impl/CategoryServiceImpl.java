package com.graduationproject.vinoeshop.service.impl;

import com.graduationproject.vinoeshop.model.Category;
import com.graduationproject.vinoeshop.model.Type;
import com.graduationproject.vinoeshop.model.exceptions.InvalidCategoryIdException;
import com.graduationproject.vinoeshop.repository.CategoryRepository;
import com.graduationproject.vinoeshop.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
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
    public Category update(Long categoryId, String name, List<Type> types) {
        return null;
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
        return null;
    }
}
