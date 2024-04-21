package com.example.main.services.category;


import com.example.main.dtos.CategoryDTO;
import com.example.main.entities.Category;
import com.example.main.exceptions.IdNotFoundException;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDTO categoryDTO);
    Category updateCategory(Long categoryId, CategoryDTO categoryDTO) throws IdNotFoundException;
    void deleteCategory(Long categoryId) throws IdNotFoundException;
    Category getCategory(Long categoryId) throws IdNotFoundException;
    List<Category> getCategories() ;
}
