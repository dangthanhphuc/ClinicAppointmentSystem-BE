package com.example.main.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorRegisterAccountDTO {

    @JsonProperty("username")
    @NotEmpty(message = "Username can not be empty !")
    private String username;

    @JsonProperty("password")
    @NotEmpty(message = "Password can not be empty !")
    private String password;

    @JsonProperty("retype_password")
    @NotEmpty(message = "Retype password can not be empty !")
    public String retypePassword;

    @JsonProperty("clinic_id")
    @Min(value = 1, message = "Clinic ID must be greater than 1")
    private Long clinicId;

    @JsonProperty("doctor_id")
    @Min(value = 1, message = "Doctor ID must be greater than 1")
    private Long doctorId;
}
