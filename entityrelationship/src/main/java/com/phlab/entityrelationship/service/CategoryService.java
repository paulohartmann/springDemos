package com.phlab.entityrelationship.service;

import com.phlab.entityrelationship.dao.CategoryDao;
import com.phlab.entityrelationship.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/*
 * Author: phlab
 * Date: 03/02/21
 */
@Service
public class CategoryService {

    private final CategoryDao categoryDao;

    @Autowired
    public CategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public void addCategory(Category c) {
        categoryDao.save(c);
    }

    public Iterable<Category> findAll() {
        Iterable<Category> categories = categoryDao.findAll();
        return categories;
    }

    public Category findById(Long id) {
        return categoryDao.findById(id).orElse(null);
    }

    public Category updateCategory(Long id, Category category) {
        if (category.getName().trim().length() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category Name is obligatory");
        }

        Category toUpdate = categoryDao.getOne(id);
        if (toUpdate.getIdCategory() > 0) {
            toUpdate.setName(category.getName());
            return categoryDao.save(toUpdate);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category not found");
        }
    }
}
