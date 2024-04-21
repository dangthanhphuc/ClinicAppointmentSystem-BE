package com.example.main.repositories;

import com.example.main.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query(value = "SELECT a.* FROM appointments  a WHERE a.doctor_id = :doctorId", nativeQuery = true)
    List<Appointment> findByDoctorId(@Param("doctorId") Long doctorId);
    @Query(value = "SELECT a.* FROM appointments a WHERE a.patient_id = :patientId", nativeQuery = true)
    List<Appointment> findByPatientId(@Param("patientId") Long patientId);

}
