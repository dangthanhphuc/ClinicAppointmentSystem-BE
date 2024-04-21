package com.example.main.responses;

import com.example.main.entities.Doctor;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorResponse {
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

    @JsonProperty("clinic_name")
    private String clinicName;

    public static DoctorResponse fromDoctor(Doctor doctor){
        return DoctorResponse.builder()
                .email(doctor.getEmail())
                .name(doctor.getName())
                .address(doctor.getAddress())
                .phoneNumber(doctor.getPhoneNumber())
                .dateOfBirth(doctor.getDateOfBirth())
                .gender(doctor.isGender()  ? "Nam" : "Nữ")
                .clinicName(doctor.getClinic().getName())
                .build();
    }
}
