package com.example.main.controllers;

import com.example.main.dtos.DoctorDTO;
import com.example.main.dtos.DoctorRegisterAccountDTO;
import com.example.main.entities.Doctor;
import com.example.main.exceptions.IdNotFoundException;
import com.example.main.exceptions.NotMatchException;
import com.example.main.exceptions.PayloadTooLargeException;
import com.example.main.exceptions.UnsupportedMediaTypeException;
import com.example.main.filters.InputInvalidFilter;
import com.example.main.responses.DoctorResponse;
import com.example.main.responses.ResponseObject;
import com.example.main.services.doctor.IDoctorService;
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
@RequestMapping("${api.prefix}/doctors")
@RestController
public class DoctorController {
    private final IDoctorService doctorService;

    @PreAuthorize("hasRole('ROLE_CLINIC_MANAGER')")
    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createDoctor(
            @Valid @RequestBody DoctorDTO doctorDTO,
            BindingResult result
    ) throws IdNotFoundException {

        ResponseEntity<ResponseObject> isInvalid = InputInvalidFilter.checkInvalidInput(result);
        if(isInvalid != null) {
            return isInvalid;
        }

        Doctor doctor = doctorService.createDoctor(doctorDTO);

        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Doctor created successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(DoctorResponse.fromDoctor(doctor))
                        .build()
        );
    }

    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @PutMapping("/update/{doctorId}")
    public ResponseEntity<ResponseObject> updateDoctor(
            @PathVariable Long doctorId,
            @Valid @RequestBody DoctorDTO doctorDTO,
            BindingResult result
    ) throws IdNotFoundException {
        ResponseEntity<ResponseObject> isInvalid = InputInvalidFilter.checkInvalidInput(result);
        if(isInvalid != null) {
            return isInvalid;
        }

        Doctor doctor = doctorService
                .updateDoctor(doctorId, doctorDTO);

        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Doctor updated successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(DoctorResponse.fromDoctor(doctor))
                        .build()
        );
    }

    @PreAuthorize("hasRole('ROLE_CLINIC_MANAGER')")
    @DeleteMapping("/delete/{doctorId}")
    public ResponseEntity<ResponseObject> deleteDoctor(
            @PathVariable Long doctorId
    ) throws IdNotFoundException {
        doctorService.deleteDoctor(doctorId);

        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Doctor deleted successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data("Delete successfully !")
                        .build()
        );
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<ResponseObject> getDoctor(
            @PathVariable Long doctorId
    ) throws IdNotFoundException {
        Doctor doctor = doctorService.getDoctor(doctorId);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Clinic get successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(DoctorResponse.fromDoctor(doctor))
                        .build()
        );
    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> getDoctors() {
        List<Doctor> doctors = doctorService.getDoctors();
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Doctor get successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(doctors.stream().map(DoctorResponse::fromDoctor))
                        .build()
        );
    }

    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @PostMapping(value = "/uploads/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseObject> uploadImages(
            @PathVariable("id") Long doctorId,
            @ModelAttribute("files") List<MultipartFile> files
    ) throws IOException, IdNotFoundException, UnsupportedMediaTypeException, PayloadTooLargeException {

        for(MultipartFile file : files) {
            doctorService.uploadImage(doctorId, file);
        }

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

    @PostMapping("/register")
    public ResponseEntity<ResponseObject> registerAccount(
            @Valid @RequestBody DoctorRegisterAccountDTO doctorRegisterAccountDTO,
            BindingResult result
    ) throws IdNotFoundException, NotMatchException {

        ResponseEntity<ResponseObject> isInvalid = InputInvalidFilter.checkInvalidInput(result);
        if(isInvalid != null) {
            return isInvalid;
        }

        if(!doctorRegisterAccountDTO.getPassword().equals(doctorRegisterAccountDTO.getRetypePassword())){
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

        Doctor doctor = doctorService.registerAccount(doctorRegisterAccountDTO);

        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Doctor register account successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(DoctorResponse.fromDoctor(doctor))
                        .build()
        );

    }

}
