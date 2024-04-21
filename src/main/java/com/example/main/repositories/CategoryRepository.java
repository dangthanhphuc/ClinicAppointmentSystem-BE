package com.example.main.repositories;

import com.example.main.entities.AppointmentType;
import com.example.main.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryIdAndIsDeletedFalse(@Param("categoryId") Long categoryId);

}
