package com.example.main.services.category;

import com.example.main.dtos.CategoryDTO;
import com.example.main.entities.AppointmentType;
import com.example.main.entities.Category;
import com.example.main.exceptions.IdNotFoundException;
import com.example.main.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService implements ICategoryService{
    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Category createCategory(CategoryDTO categoryDTO) {
        Category category = new Category();

        modelMapper.map(categoryDTO, category);

        return categoryRepository.save(category);

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Category updateCategory(Long categoryId, CategoryDTO categoryDTO) throws IdNotFoundException {
        Category existingCategory = categoryRepository
                .findByCategoryIdAndIsDeletedFalse(categoryId)
                .orElseThrow(
                        () -> new IdNotFoundException("Category id : " + categoryId +" is not found")
                );

        modelMapper.map(categoryDTO, existingCategory);

        return categoryRepository.save(existingCategory);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteCategory(Long categoryId) throws IdNotFoundException {
        Category existingCategory = categoryRepository
                .findByCategoryIdAndIsDeletedFalse(categoryId)
                .orElseThrow(
                        () -> new IdNotFoundException("Category id : " + categoryId +" is not found")
                );
        existingCategory.setDeleted(true);
        categoryRepository.save(existingCategory);
    }

    @Override
    public Category getCategory(Long categoryId) throws IdNotFoundException {
        return categoryRepository
                .findByCategoryIdAndIsDeletedFalse(categoryId)
                .orElseThrow(
                        () -> new IdNotFoundException("Category id : " + categoryId +" is not found")
                );
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }
}
