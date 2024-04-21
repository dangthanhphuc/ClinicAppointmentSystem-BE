package com.example.main.repositories;

import com.example.main.entities.Category;
import com.example.main.entities.Clinic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    Optional<Clinic> findByClinicIdAndIsDeletedFalse(@Param("clinicId") Long clinicId);

    @Query(value = "SELECT *" +
            " FROM clinics c" +
            " WHERE c.is_deleted = false" +
            " AND (:keyword IS NULL OR :keyword = '' " +
            " OR c.name LIKE %:keyword%" +
            " OR c.address LIKE %:keyword%" +
            " OR c.email LIKE %:keyword%" +
            " OR c.description LIKE %:keyword% )"
            , nativeQuery = true)
    Page<Clinic> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
