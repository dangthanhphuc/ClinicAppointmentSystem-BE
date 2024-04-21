package com.example.main.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDTO {
    @JsonProperty("email")
    private String email;

    @JsonProperty("name")
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @JsonProperty("address")
    private String address;

    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number cannot be blank")
    private String phoneNumber;

    @JsonProperty("date_of_birth")
    @NotNull(message = "Phone number cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @JsonProperty("gender")
    @NotNull(message = "Gender cannot be null")
    private boolean gender;

    @JsonProperty("clinic_id")
    @Min(value = 1, message = "Clinic ID must be greater than 1")
    private Long clinicId;
}
