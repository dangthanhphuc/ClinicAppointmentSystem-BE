package com.example.main.repositories;

import com.example.main.entities.Appointment;
import com.example.main.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByDoctorIdAndIsDeletedFalse(@Param("doctorId") Long doctorId);
}
