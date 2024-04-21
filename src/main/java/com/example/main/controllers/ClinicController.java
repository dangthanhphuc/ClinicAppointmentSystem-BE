package com.example.main.controllers;

import com.example.main.dtos.ClinicDTO;
import com.example.main.entities.Clinic;
import com.example.main.exceptions.IdNotFoundException;
import com.example.main.filters.InputInvalidFilter;
import com.example.main.responses.ClinicListResponse;
import com.example.main.responses.ClinicResponse;
import com.example.main.responses.ResponseObject;
import com.example.main.services.clinic.IClinicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RequestMapping("${api.prefix}/clinics")
@RestController
public class ClinicController {
    private final IClinicService clinicService;
    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createClinic(
            @Valid @RequestBody ClinicDTO clinicDTO,
            BindingResult result
    ) throws IdNotFoundException {

        ResponseEntity<ResponseObject> isInvalid = InputInvalidFilter.checkInvalidInput(result);
        if(isInvalid != null) {
            return isInvalid;
        }

        Clinic clinic = clinicService.createClinic(clinicDTO);

        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Clinic created successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(ClinicResponse.fromClinic(clinic))
                        .build()
        );
    }

    @PutMapping("/update/{clinicId}")
    public ResponseEntity<ResponseObject> updateClinic(
            @PathVariable Long clinicId,
            @Valid @RequestBody ClinicDTO clinicDTO,
            BindingResult result
    ) throws IdNotFoundException {
        ResponseEntity<ResponseObject> isInvalid = InputInvalidFilter.checkInvalidInput(result);
        if(isInvalid != null) {
            return isInvalid;
        }

        Clinic Clinic = clinicService
                .updateClinic(clinicId, clinicDTO);

        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Clinic updated successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(ClinicResponse.fromClinic(Clinic))
                        .build()
        );
    }

    @DeleteMapping("/delete/{clinicId}")
    public ResponseEntity<ResponseObject> deleteClinic(
            @PathVariable Long clinicId
    ) throws IdNotFoundException {
        clinicService.deleteClinic(clinicId);

        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Clinic deleted successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data("Delete successfully !")
                        .build()
        );
    }

    @GetMapping("/{clinicId}")
    public ResponseEntity<ResponseObject> getClinic(
            @PathVariable Long clinicId
    ) throws IdNotFoundException {
        Clinic clinic = clinicService.getClinic(clinicId);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Clinic get successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(ClinicResponse.fromClinic(clinic))
                        .build()
        );
    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> getClinics() {
        List<Clinic> clinic = clinicService.getClinics();
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Clinic get successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(clinic.stream().map(ClinicResponse::fromClinic))
                        .build()
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseObject> getClinicsByKeyword(
            @RequestParam(defaultValue = "", required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int limit
    ) {
        PageRequest pageRequest = PageRequest.of(
                page,
                limit,
                Sort.by("name").ascending()
        );

        Page<ClinicResponse> clinicPages = clinicService
                .getClinicsByKeyword(keyword, pageRequest)
                .map(ClinicResponse::fromClinic);

        // Lấy tổng số trang
        int totalPages = clinicPages.getTotalPages();
        List<ClinicResponse> orderResponses = clinicPages.getContent();
        // Đổi sang kiểu dữ liệu trả về
        ClinicListResponse clinicListResponse = ClinicListResponse
                .builder()
                .clinics(orderResponses)
                .totalPages(totalPages)
                .build();

        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Clinic list get successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(clinicListResponse)
                        .build()
        );

    }

}
