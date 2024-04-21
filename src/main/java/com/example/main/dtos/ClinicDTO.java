package com.example.main.dtos;

import com.example.main.entities.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClinicDTO {

    @JsonProperty("name")
    @NotBlank(message = "Clinic name cannot be blank")
    private String name;

    @JsonProperty("email")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @JsonProperty("address")
    @NotBlank(message = "Address cannot be blank")
    private String address;

    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number cannot be blank")
    private String phoneNumber;

    @JsonProperty("description")
    private String description;

    @JsonProperty("category_id")
    @Min(value = 1, message = "Category ID must be greater than 1")
    private Long categoryId;

}
