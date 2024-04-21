package com.example.main.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentTypeDTO {

    @JsonProperty("type_name")
    @NotBlank(message = "Type name cannot is blank !")
    private String typeName;

    @JsonProperty("description")
    private String description;
}
