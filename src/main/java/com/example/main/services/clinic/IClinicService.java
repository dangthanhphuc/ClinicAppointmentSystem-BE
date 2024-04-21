package com.example.main.services.clinic;

import com.example.main.dtos.ClinicDTO;
import com.example.main.entities.Clinic;
import com.example.main.exceptions.IdNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IClinicService {
    Clinic createClinic(ClinicDTO clinicDTO) throws IdNotFoundException;
    Clinic updateClinic(Long clinicId, ClinicDTO clinicDTO) throws IdNotFoundException;
    void deleteClinic(Long clinicId) throws IdNotFoundException;
    Clinic getClinic(Long clinicId) throws IdNotFoundException;
    List<Clinic> getClinics() ;
    Page<Clinic> getClinicsByKeyword(String keyword, Pageable pageable);
}
