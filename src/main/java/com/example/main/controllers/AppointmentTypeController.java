package com.example.main.controllers;

import com.example.main.dtos.AppointmentDTO;
import com.example.main.dtos.AppointmentTypeDTO;
import com.example.main.entities.AppointmentType;
import com.example.main.exceptions.IdNotFoundException;
import com.example.main.exceptions.InvalidDataException;
import com.example.main.filters.InputInvalidFilter;
import com.example.main.repositories.AppointmentRepository;
import com.example.main.responses.AppointmentTypeResponse;
import com.example.main.responses.ResponseObject;
import com.example.main.services.appointmenttype.IAppointmentTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RequestMapping("${api.prefix}/appointment_types")
@RestController
public class AppointmentTypeController {

    private final IAppointmentTypeService appointmentTypeService;

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createAppointmentType(
            @Valid @RequestBody AppointmentTypeDTO appointmentTypeDTO,
            BindingResult result
    ) throws IdNotFoundException {

        ResponseEntity<ResponseObject> isInvalid = InputInvalidFilter.checkInvalidInput(result);
        if(isInvalid != null) {
            return isInvalid;
        }

        AppointmentType appointmentType = appointmentTypeService.createAppointmentType(appointmentTypeDTO);

        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Appointment type created successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(AppointmentTypeResponse.fromAppointmentType(appointmentType))
                        .build()
        );
    }

    @PutMapping("/update/{appointmentTypeId}")
    public ResponseEntity<ResponseObject> updateAppointmentType(
            @PathVariable Long appointmentTypeId,
            @Valid @RequestBody AppointmentTypeDTO appointmentTypeDTO,
            BindingResult result
    ) throws IdNotFoundException {
        ResponseEntity<ResponseObject> isInvalid = InputInvalidFilter.checkInvalidInput(result);
        if(isInvalid != null) {
            return isInvalid;
        }

        AppointmentType appointmentType = appointmentTypeService
                .updateAppointmentType(appointmentTypeId, appointmentTypeDTO);

        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Appointment type updated successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(AppointmentTypeResponse.fromAppointmentType(appointmentType))
                        .build()
        );
    }

    @DeleteMapping("/delete/{appointmentTypeId}")
    public ResponseEntity<ResponseObject> deleteAppointmentType(
            @PathVariable Long appointmentTypeId
    ) throws IdNotFoundException {
        appointmentTypeService.deleteAppointmentType(appointmentTypeId);

        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Appointment type deleted successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data("Delete successfully !")
                        .build()
        );
    }

    @GetMapping("/{appointmentTypeId}")
    public ResponseEntity<ResponseObject> getAppointmentType (
            @PathVariable Long appointmentTypeId
    ) throws IdNotFoundException {
        AppointmentType appointmentType = appointmentTypeService.getAppointmentType(appointmentTypeId);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Appointment type got successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(AppointmentTypeResponse.fromAppointmentType(appointmentType))
                        .build()
        );
    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> getAppointmentTypes () {
        List<AppointmentType> appointmentTypes = appointmentTypeService.getAppointmentTypes();
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Appointment type got successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(appointmentTypes.stream().map(AppointmentTypeResponse::fromAppointmentType))
                        .build()
        );
    }



}
