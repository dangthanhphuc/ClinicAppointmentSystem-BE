package com.example.main.repositories;

import com.example.main.entities.Doctor;
import com.example.main.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByPatientIdAndIsDeletedFalse(@Param("patientId") Long patientId);
}
