package com.example.main.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    @JsonProperty("username")
    @NotEmpty(message = "Username can not be empty !")
    public String username;

    @JsonProperty("password")
    @NotEmpty(message = "Password can not be empty !")
    public String password;
}
