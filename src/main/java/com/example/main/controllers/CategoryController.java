package com.example.main.controllers;


import com.example.main.dtos.CategoryDTO;
import com.example.main.entities.Category;
import com.example.main.entities.Category;
import com.example.main.exceptions.IdNotFoundException;
import com.example.main.filters.InputInvalidFilter;
import com.example.main.responses.CategoryResponse;
import com.example.main.responses.CategoryResponse;
import com.example.main.responses.ResponseObject;
import com.example.main.services.category.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RequestMapping("${api.prefix}/categories")
@RestController
public class CategoryController {
    private final ICategoryService categoryService;

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createCategory(
            @Valid @RequestBody CategoryDTO categoryDTO,
            BindingResult result
    ) throws IdNotFoundException {

        ResponseEntity<ResponseObject> isInvalid = InputInvalidFilter.checkInvalidInput(result);
        if(isInvalid != null) {
            return isInvalid;
        }

        Category Category = categoryService.createCategory(categoryDTO);

        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Category created successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(CategoryResponse.fromCategory(Category))
                        .build()
        );
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @PutMapping("/update/{categoryId}")
    public ResponseEntity<ResponseObject> updateCategory(
            @PathVariable Long categoryId,
            @Valid @RequestBody CategoryDTO categoryDTO,
            BindingResult result
    ) throws IdNotFoundException {
        ResponseEntity<ResponseObject> isInvalid = InputInvalidFilter.checkInvalidInput(result);
        if(isInvalid != null) {
            return isInvalid;
        }

        Category Category = categoryService
                .updateCategory(categoryId, categoryDTO);

        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Category updated successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(CategoryResponse.fromCategory(Category))
                        .build()
        );
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<ResponseObject> deleteCategory(
            @PathVariable Long categoryId
    ) throws IdNotFoundException {
        categoryService.deleteCategory(categoryId);

        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Category deleted successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data("Delete successfully !")
                        .build()
        );
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ResponseObject> getCategory (
            @PathVariable Long categoryId
    ) throws IdNotFoundException {
        Category category = categoryService.getCategory(categoryId);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Category got successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(CategoryResponse.fromCategory(category))
                        .build()
        );
    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> getCategories () {
        List<Category> categories = categoryService.getCategories();
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Categories got successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(categories.stream().map(CategoryResponse::fromCategory))
                        .build()
        );
    }
}
