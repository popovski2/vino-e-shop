package com.graduationproject.vinoeshop.model.exceptions;

public class InvalidCategoryIdException extends RuntimeException {
    public InvalidCategoryIdException(Long categoryId) {
        super(String.format("404, Category with id %d was not found", categoryId));
    }
}
