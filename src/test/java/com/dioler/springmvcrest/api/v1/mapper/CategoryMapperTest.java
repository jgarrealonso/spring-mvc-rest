package com.dioler.springmvcrest.api.v1.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.dioler.springmvcrest.api.v1.model.CategoryDTO;
import com.dioler.springmvcrest.domain.Category;
import org.junit.jupiter.api.Test;

public class CategoryMapperTest {

    public static final String NAME = "Joe";
    public static final long ID = 1L;
    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Test
    public void categoryToCategoryDTO() {
        //Given
        Category category = new Category();
            category.setName(NAME);
        category.setId(ID);
        //When
        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);
        //Then
        assertEquals(ID, categoryDTO.getId());
        assertEquals(NAME, categoryDTO.getName());
    }

}