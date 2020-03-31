package com.dioler.springmvcrest.api.v1.model;

import java.util.List;
import lombok.Data;

@Data
public class CategoriesDTO {
    private List<CategoryDTO> categories;
}
