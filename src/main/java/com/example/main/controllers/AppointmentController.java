package com.example.main.controllers;

import com.example.main.dtos.AppointmentDTO;
import com.example.main.entities.Appointment;
import com.example.main.exceptions.IdNotFoundException;
import com.example.main.exceptions.InvalidDataException;
import com.example.main.filters.InputInvalidFilter;
import com.example.main.responses.AppointmentResponse;
import com.example.main.responses.ResponseObject;
import com.example.main.services.appointment.IAppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RequestMapping("${api.prefix}/appointments")
@RestController
public class AppointmentController {

    private final IAppointmentService appointmentService;

    @PostMapping("/create") // Doctor
    public ResponseEntity<ResponseObject> createAppointment(
            @Valid @RequestBody AppointmentDTO appointmentDTO,
            BindingResult result
    ) throws InvalidDataException, IdNotFoundException {
        ResponseEntity<ResponseObject> isInvalid = InputInvalidFilter.checkInvalidInput(result);
        if(isInvalid != null) {
            return isInvalid;
        }

        Appointment appointment = appointmentService.createAppointment(appointmentDTO);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Appointment created successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(AppointmentResponse.fromAppointment(appointment))
                        .build()
        );
    }

    @PutMapping("/update/{appointmentId}")  // Doctor
    public ResponseEntity<ResponseObject> updateAppointment(
            @PathVariable Long appointmentId,
            @Valid @RequestBody AppointmentDTO appointmentDTO,
            BindingResult result
    ) throws IdNotFoundException {
        ResponseEntity<ResponseObject> isInvalid = InputInvalidFilter.checkInvalidInput(result);
        if(isInvalid != null) {
            return isInvalid;
        }
        Appointment appointment = appointmentService.updateAppointment(appointmentId, appointmentDTO);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Appointment update successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(AppointmentResponse.fromAppointment(appointment))
                        .build()
        );
    }

    @DeleteMapping("/delete/{appointmentId}") // Doctor
    public ResponseEntity<ResponseObject> deleteAppointment(
            @PathVariable Long appointmentId
    ) throws IdNotFoundException {
        appointmentService.deleteAppointment(appointmentId);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Appointment delete successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data("Delete successfully !")
                        .build()
        );
    }

    @GetMapping("/{appointmentId}") // Manager
    public ResponseEntity<ResponseObject> getAppointment(
            @PathVariable Long appointmentId
    ) throws IdNotFoundException {
        Appointment appointment = appointmentService.getAppointment(appointmentId);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Get appointment by appointment id is successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(AppointmentResponse.fromAppointment(appointment))
                        .build()
        );
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<ResponseObject> getAppointmentsByPatientId(
            @PathVariable Long patientId
    ){
        List<Appointment> appointments = appointmentService.getAppointmentsByPatientId(patientId);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Get appointments by patient id is successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(appointments.stream().map(AppointmentResponse::fromAppointment))
                        .build()
        );
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<ResponseObject> getAppointmentsByDoctorId(
            @PathVariable Long doctorId
    ) {
        List<Appointment> appointments = appointmentService.getAppointmentsByDoctorId(doctorId);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Get appointments by doctor id is successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(appointments.stream().map(AppointmentResponse::fromAppointment))
                        .build()
        );
    }

    @GetMapping("") // Manager
    public ResponseEntity<ResponseObject> getAppointments() {
        List<Appointment> appointments = appointmentService.getAppointments();
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Get appointments is successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(appointments.stream().map(AppointmentResponse::fromAppointment))
                        .build()
        );
    }


    @PutMapping("/patient/booking") // Patient
    public ResponseEntity<ResponseObject> bookingAppointmentFromPatient(
            @RequestParam Long appointmentId,
            @RequestParam Long patientId
    ) throws IdNotFoundException {
        Appointment appointment = appointmentService.bookingAppointment(appointmentId, patientId);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Book appointment successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(AppointmentResponse.fromAppointment(appointment))
                        .build()
        );
    }

    @DeleteMapping("/patient/cancellation/{appointmentId}") // Patient
    public ResponseEntity<ResponseObject> cancellationOfBooking(
            @PathVariable Long appointmentId
    ) throws IdNotFoundException {
        appointmentService.cancellationOfBooking(appointmentId);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Book appointment successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data("Delete appointment successfully !")
                        .build()
        );
    }
}
