package com.example.main.services.clinic;

import com.example.main.dtos.ClinicDTO;
import com.example.main.entities.Category;
import com.example.main.entities.Clinic;
import com.example.main.exceptions.IdNotFoundException;
import com.example.main.repositories.CategoryRepository;
import com.example.main.repositories.ClinicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClinicService implements IClinicService {

    private final CategoryRepository categoryRepository;
    private final ClinicRepository clinicRepository;

    @Override
    public Clinic createClinic(ClinicDTO clinicDTO) throws IdNotFoundException {
        Category existingCategory = categoryRepository
                .findByCategoryIdAndIsDeletedFalse(clinicDTO.getCategoryId())
                .orElseThrow(
                        () -> new IdNotFoundException("Category id " + clinicDTO.getCategoryId() + " is not found")
                );

        Clinic clinic = Clinic.builder()
                .name(clinicDTO.getName())
                .email(clinicDTO.getEmail())
                .address(clinicDTO.getAddress())
                .phoneNumber(clinicDTO.getPhoneNumber())
                .description(clinicDTO.getDescription())
                .category(existingCategory)
                .build();

        return clinicRepository.save(clinic);
    }

    @Override
    public Clinic updateClinic(Long clinicId, ClinicDTO clinicDTO) throws IdNotFoundException {
        Category existingCategory = categoryRepository
                .findByCategoryIdAndIsDeletedFalse(clinicDTO.getCategoryId())
                .orElseThrow(
                        () -> new IdNotFoundException("Category id " + clinicDTO.getCategoryId() + " is not found")
                );
        Clinic existingClinic = clinicRepository
                .findByClinicIdAndIsDeletedFalse(clinicId)
                .orElseThrow(
                        () -> new IdNotFoundException("Clinic id " + clinicId + " is not found")
                );

        existingClinic.setName(clinicDTO.getName());
        existingClinic.setEmail(clinicDTO.getEmail());
        existingClinic.setAddress(clinicDTO.getAddress());
        existingClinic.setPhoneNumber(clinicDTO.getPhoneNumber());
        existingClinic.setPhoneNumber(clinicDTO.getPhoneNumber());
        existingClinic.setCategory(existingCategory);

        return clinicRepository.save(existingClinic);
    }

    @Override
    public void deleteClinic(Long clinicId) throws IdNotFoundException {
        Clinic existingClinic = clinicRepository
                .findByClinicIdAndIsDeletedFalse(clinicId)
                .orElseThrow(
                        () -> new IdNotFoundException("Clinic id " + clinicId + " is not found")
                );
        existingClinic.setDeleted(true);
        clinicRepository.save(existingClinic);
    }

    @Override
    public Clinic getClinic(Long clinicId) throws IdNotFoundException {
        return clinicRepository
                .findByClinicIdAndIsDeletedFalse(clinicId)
                .orElseThrow(
                        () -> new IdNotFoundException("Clinic id " + clinicId + " is not found")
                );
    }

    @Override
    public List<Clinic> getClinics() {
        return clinicRepository.findAll();
    }

    @Override
    public Page<Clinic> getClinicsByKeyword(String keyword, Pageable pageable) {
        return clinicRepository.findByKeyword(keyword, pageable);
    }
}
