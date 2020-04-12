package com.dioler.springmvcrest.services;

import com.dioler.springmvcrest.api.v1.mapper.CategoryMapper;
import com.dioler.springmvcrest.api.v1.model.CategoryDTO;
import com.dioler.springmvcrest.repositories.CategoryRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
            .map(categoryMapper::categoryToCategoryDTO)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<CategoryDTO> getCategoryByName(String name) {
        return categoryRepository.findByName(name)
            .map(categoryMapper::categoryToCategoryDTO);
    }
}
