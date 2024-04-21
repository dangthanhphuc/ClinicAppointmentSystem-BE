package com.example.main.responses;

import com.example.main.entities.Role;
import com.example.main.entities.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    @JsonProperty("username")
    private String userName;

    @JsonProperty("password")
    private String password;

    @JsonProperty("role_name")
    private String roleName;

    public static UserResponse fromUser(User user) {
        return UserResponse.builder()
                .userName(user.getUsername())
                .password(user.getPassword())
                .roleName(user.getRole().getName())
                .build();
    }
}
