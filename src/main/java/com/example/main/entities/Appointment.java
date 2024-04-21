package com.example.main.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;

    @Column(name = "available_time_start", nullable = false)
    private LocalDateTime availableTimeStart;

    @Column(name = "available_time_end", nullable = false)
    private LocalDateTime availableTimeEnd;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "note_doctor")
    private String noteDoctor;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "appointment_type_id")
    private AppointmentType appointmentType;

    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id") // Có thể null
    private Patient patient;



}
