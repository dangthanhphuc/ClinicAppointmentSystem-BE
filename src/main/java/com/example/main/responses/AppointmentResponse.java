package com.example.main.responses;

import com.example.main.entities.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {
    
    @JsonProperty("available_time_start")
    private LocalDateTime availableTimeStart;

    @JsonProperty("available_time_end")
    private LocalDateTime availableTimeEnd;

    @JsonProperty("status")
    private String status;

    @JsonProperty("note_doctor")
    private String noteDoctor;

    @JsonProperty("room_name")
    private String roomName;

    @JsonProperty("appointment_type_name")
    private String appointmentTypeName;

    @JsonProperty("doctor_name")
    private String doctorName;

    @JsonProperty("patient_name")
    private String patientName;

    public static AppointmentResponse fromAppointment(Appointment appointment){
        return AppointmentResponse.builder()
                .availableTimeStart(appointment.getAvailableTimeStart())
                .availableTimeEnd(appointment.getAvailableTimeEnd())
                .status(appointment.getStatus())
                .noteDoctor(appointment.getNoteDoctor())
                .roomName(appointment.getRoom().getName())
                .doctorName(appointment.getDoctor().getName())
                .patientName(appointment.getPatient().getName())
                .build();
    }
}
