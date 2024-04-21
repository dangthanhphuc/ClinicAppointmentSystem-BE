package com.example.main.repositories;

import com.example.main.entities.AppointmentType;
import com.example.main.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppointmentTypeRepository extends JpaRepository<AppointmentType, Long> {
    Optional<AppointmentType> findByAppointmentTypeIdAndIsDeletedFalse(@Param("appointmentTypeId") Long appointmentTypeId);
}
