package com.example.main.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {

    @JsonProperty("name")
    @NotBlank(message = "The name of the room cannot be blank !")
    private String name;

    @JsonProperty("clinic_id")
    @Min(value = 1, message = "The clinic id must be greater than 0")
    private Long clinicId;


}
