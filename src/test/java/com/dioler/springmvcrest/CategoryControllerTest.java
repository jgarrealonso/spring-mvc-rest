package com.dioler.springmvcrest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dioler.springmvcrest.api.v1.model.CategoryDTO;
import com.dioler.springmvcrest.services.CategoryService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class CategoryControllerTest {

    public static final String NAME = "fruits";
    @Mock
    CategoryService categoryService;

    @InjectMocks
    CategoryController categoryController;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    void getAllCategories() throws Exception {

        //Given
        CategoryDTO categoryFruits = new CategoryDTO();
        categoryFruits.setId(1L);
        categoryFruits.setName("Fruits");

        CategoryDTO categoryNuts = new CategoryDTO();
        categoryNuts.setId(2L);
        categoryNuts.setName("Nuts");

        List<CategoryDTO> categories = Arrays.asList(categoryFruits, categoryNuts);

        when(categoryService.getAllCategories()).thenReturn(categories);

        //When
        mockMvc.perform(get("/api/v1/categories")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.categories", hasSize(2)));
    }

    @Test
    void getCategory() throws Exception {
        //Given
        CategoryDTO categoryFruits = new CategoryDTO();
        categoryFruits.setId(1L);
        categoryFruits.setName(NAME);

        when(categoryService.getCategoryByName(anyString())).thenReturn(Optional.of(categoryFruits));

        mockMvc.perform(
            get("/api/v1/categories/fruits")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name",equalTo(NAME)));
    }

    @Test
    void getNotFoundWhenCategoryNotExisting() throws Exception {
        when(categoryService.getCategoryByName(anyString())).thenReturn(Optional.empty());
        mockMvc.perform(
            get("/api/v1/categories/fruits")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }
}