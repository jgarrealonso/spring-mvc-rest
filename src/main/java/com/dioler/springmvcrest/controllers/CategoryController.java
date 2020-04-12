package com.dioler.springmvcrest.controllers;

import com.dioler.springmvcrest.api.v1.model.CategoriesDTO;
import com.dioler.springmvcrest.api.v1.model.CategoryDTO;
import com.dioler.springmvcrest.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    final CategoryService categoryService;

    @GetMapping
    public CategoriesDTO getAllCategories() {
        CategoriesDTO categoriesDTO = new CategoriesDTO();
        categoriesDTO.setCategories(categoryService.getAllCategories());
        return categoriesDTO;
    }

    @GetMapping("{name}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable String name) {
        return ResponseEntity.of(categoryService.getCategoryByName(name));
    }
}
