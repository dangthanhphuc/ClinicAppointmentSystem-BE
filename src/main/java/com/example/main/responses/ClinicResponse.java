package com.example.main.responses;

import com.example.main.entities.Clinic;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClinicResponse {
    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("address")
    private String address;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("description")
    private String description;

    @JsonProperty("category_name")
    private String categoryName;

    public static ClinicResponse fromClinic(Clinic clinic){
        return ClinicResponse.builder()
                .name(clinic.getName())
                .email(clinic.getEmail())
                .address(clinic.getAddress())
                .phoneNumber(clinic.getPhoneNumber())
                .description(clinic.getDescription())
                .categoryName(clinic.getCategory().getName())
                .build();
    }
}
