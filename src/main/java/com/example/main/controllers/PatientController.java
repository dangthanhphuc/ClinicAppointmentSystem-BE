package com.example.main.controllers;

import com.example.main.dtos.PatientDTO;
import com.example.main.dtos.PatientRegisterAccountDTO;
import com.example.main.entities.Patient;
import com.example.main.exceptions.IdNotFoundException;
import com.example.main.exceptions.PayloadTooLargeException;
import com.example.main.exceptions.UnsupportedMediaTypeException;
import com.example.main.filters.InputInvalidFilter;
import com.example.main.responses.PatientResponse;
import com.example.main.responses.ResponseObject;
import com.example.main.services.patient.IPatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RequestMapping("${api.prefix}/patients")
@RestController
public class PatientController {
    private final IPatientService patientService;

    @PostMapping("/register")
    public ResponseEntity<ResponseObject> registerPatient(
            @Valid @RequestBody PatientRegisterAccountDTO patientRegisterAccountDTO,
            BindingResult result
    ) throws IdNotFoundException {

        ResponseEntity<ResponseObject> isInvalid = InputInvalidFilter.checkInvalidInput(result);
        if(isInvalid != null) {
            return isInvalid;
        }

        if(!patientRegisterAccountDTO.getPassword().equals(patientRegisterAccountDTO.getRetypePassword())){
            return ResponseEntity.badRequest().body(
                    ResponseObject.builder()
                            .timeStamp(LocalDateTime.now())
                            .message("Password not match with retype password !")
                            .status(BAD_REQUEST)
                            .statusCode(BAD_REQUEST.value())
                            .data(null)
                            .build()
            );
        }

        Patient patient = patientService.registerPatient(patientRegisterAccountDTO);

        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Patient created successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(PatientResponse.fromPatient(patient))
                        .build()
        );
    }

    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @PutMapping("/update/{patientId}")
    public ResponseEntity<ResponseObject> updatePatient(
            @PathVariable Long patientId,
            @Valid @RequestBody PatientDTO patientDTO,
            BindingResult result
    ) throws IdNotFoundException {
        ResponseEntity<ResponseObject> isInvalid = InputInvalidFilter.checkInvalidInput(result);
        if(isInvalid != null) {
            return isInvalid;
        }

        Patient Patient = patientService
                .updatePatient(patientId, patientDTO);

        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Patient updated successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(PatientResponse.fromPatient(Patient))
                        .build()
        );
    }

    @PreAuthorize("hasRole('ROLE_CLINIC_MANAGER')")
    @DeleteMapping("/delete/{patientId}")
    public ResponseEntity<ResponseObject> deletePatient(
            @PathVariable Long patientId
    ) throws IdNotFoundException {
        patientService.deletePatient(patientId);

        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Patient deleted successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data("Delete successfully !")
                        .build()
        );
    }

    @PreAuthorize("hasRole('ROLE_CLINIC_MANAGER')")
    @GetMapping("/{patientId}")
    public ResponseEntity<ResponseObject> getPatient(
            @PathVariable Long patientId
    ) throws IdNotFoundException {
        Patient patient = patientService.getPatient(patientId);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Clinic get successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(PatientResponse.fromPatient(patient))
                        .build()
        );
    }

    @PreAuthorize("hasRole('ROLE_CLINIC_MANAGER')")
    @GetMapping("")
    public ResponseEntity<ResponseObject> getPatients() {
        List<Patient> patients = patientService.getPatients();
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Patient get successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(patients.stream().map(PatientResponse::fromPatient))
                        .build()
        );
    }

    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @PostMapping(value = "/uploads/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseObject> uploadImages(
            @PathVariable("id") Long patientId,
            @ModelAttribute("file") MultipartFile file
    ) throws IOException, IdNotFoundException, UnsupportedMediaTypeException, PayloadTooLargeException {


        patientService.uploadImage(patientId, file);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Successfully upload images !")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .data("Uploads successfully !")
                        .build()
        );
    }

}
