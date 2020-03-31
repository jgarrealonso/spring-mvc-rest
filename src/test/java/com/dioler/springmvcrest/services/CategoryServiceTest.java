package com.dioler.springmvcrest.services;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.dioler.springmvcrest.api.v1.mapper.CategoryMapper;
import com.dioler.springmvcrest.api.v1.model.CategoryDTO;
import com.dioler.springmvcrest.domain.Category;
import com.dioler.springmvcrest.repositories.CategoryRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


class CategoryServiceTest {

    public static final String NAME = "Fruits";
    public static final long ID = 1L;
    @Mock
    CategoryRepository categoryRepository;
    @Mock
    CategoryMapper categoryMapper;

    @InjectMocks
    CategoryServiceImpl categoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllCategories() {

        //Given
        List<Category> categories = Arrays.asList(new Category(), new Category(), new Category());
        when(categoryRepository.findAll()).thenReturn(categories);

        //When
        List<CategoryDTO> categoriesDTO = categoryService.getAllCategories();

        //Then
        assertEquals(3, categoriesDTO.size());
    }

    @Test
    void getCategoryByName() {
        Category category = new Category();
        category.setName(NAME);
        category.setId(ID);

        //Given
        when(categoryRepository.findByName(anyString())).thenReturn(category);

        //When
        CategoryDTO categoryDTO = categoryService.getCategoryByName(NAME).get();

        //Then
        assertEquals(ID, categoryDTO.getId());
        assertEquals(NAME, categoryDTO.getName());


    }
}