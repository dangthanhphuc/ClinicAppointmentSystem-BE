package com.example.main.responses;

import com.example.main.entities.Appointment;
import com.example.main.entities.AppointmentType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentTypeResponse {

    @JsonProperty("type_name")
    private String typeName;

    @JsonProperty("description")
    private String description;

    public static AppointmentTypeResponse fromAppointmentType(AppointmentType appointmentType){
        return AppointmentTypeResponse.builder()
                .typeName(appointmentType.getTypeName())
                .description(appointmentType.getDescription())
                .build();
    }
}
