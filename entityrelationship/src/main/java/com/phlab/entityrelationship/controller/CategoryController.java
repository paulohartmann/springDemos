package com.phlab.entityrelationship.controller;

import com.phlab.entityrelationship.entity.Category;
import com.phlab.entityrelationship.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*
 * Author: phlab
 * Date: 03/02/21
 */
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public void addCategory(@RequestBody Category category){
        categoryService.addCategory(category);
    }

    @GetMapping
    public Iterable<Category> findAll(){
        return categoryService.findAll();
    }
}
