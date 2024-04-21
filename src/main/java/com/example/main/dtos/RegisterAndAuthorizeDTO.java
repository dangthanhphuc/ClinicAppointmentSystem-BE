package com.example.main.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterAndAuthorizeDTO {
    @JsonProperty("username")
    @NotEmpty(message = "Username can not be empty !")
    private String username;

    @JsonProperty("password")
    @NotBlank(message = "Password can not be empty !")
    private String password;

    @JsonProperty("retype_password")
    @NotBlank(message = "Retype password can not be blank !")
    public String retypePassword;

    @JsonProperty("role_id")
    @Min(value = 1, message = "Role id can not be empty !")
    private Long roleId;
}
