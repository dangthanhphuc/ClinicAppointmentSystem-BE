package com.example.main.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookAppointmentDTO {
    @JsonProperty("appointment_id")
    private Long appointmentId;
    @JsonProperty("patient_id")
    private Long patientId;
}
