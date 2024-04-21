package com.example.main.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;


import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {

    @JsonProperty("available_time_start")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
//    @JsonDeserialize(converter = LocalDateTimeConverter.class)
    @NotNull(message = "The available time of doctor cannot be null !")
    private LocalDateTime availableTimeStart;

    @JsonProperty("available_time_end")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
//    @JsonDeserialize(converter = LocalDateTimeConverter.class)
    @NotNull(message = "The available time of doctor cannot be null !")
    private LocalDateTime availableTimeEnd;

    @JsonProperty("status")
    @NotNull(message = "The status of appointment cannot be null !")
    private String status;

    @JsonProperty("note_doctor")
    private String noteDoctor;

    @JsonProperty("room_id")
    @NotNull(message = "The room id cannot be null !")
    private Long roomId;

    @JsonProperty("appointment_type_id")
    @NotNull(message = "The appointment type id cannot be null !")
    private Long appointmentTypeId;

    @JsonProperty("doctor_id")
    @NotNull(message = "The doctor id cannot be null !")
    private Long doctorId;


}
