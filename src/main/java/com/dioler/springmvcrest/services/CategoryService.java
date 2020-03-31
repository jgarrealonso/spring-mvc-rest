package com.dioler.springmvcrest.services;

import com.dioler.springmvcrest.api.v1.model.CategoryDTO;
import java.util.List;
import java.util.Optional;

public interface CategoryService {

     List<CategoryDTO> getAllCategories();

     Optional<CategoryDTO> getCategoryByName(String name);
}
