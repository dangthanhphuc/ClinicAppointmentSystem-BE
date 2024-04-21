package com.example.main.responses;

import com.example.main.entities.Doctor;
import com.example.main.entities.Patient;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponse {
    @JsonProperty("email")
    private String email;

    @JsonProperty("name")
    private String name;

    @JsonProperty("address")
    private String address;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("date_of_birth")
    private LocalDate dateOfBirth;

    @JsonProperty("gender")
    private String gender;

    public static PatientResponse fromPatient(Patient patient){
        return PatientResponse.builder()
                .email(patient.getEmail())
                .name(patient.getName())
                .address(patient.getAddress())
                .phoneNumber(patient.getPhoneNumber())
                .dateOfBirth(patient.getDateOfBirth())
                .gender(patient.isGender() ? "Nam" : "Nữ")
                .build();
    }
}
