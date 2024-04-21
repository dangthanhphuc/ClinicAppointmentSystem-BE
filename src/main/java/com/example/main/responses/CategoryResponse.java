package com.example.main.responses;

import com.example.main.entities.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {

    @JsonProperty("name")
    private String name;

    public static CategoryResponse fromCategory(Category category){
        return CategoryResponse.builder()
                .name(category.getName())
                .build();
    }
}
