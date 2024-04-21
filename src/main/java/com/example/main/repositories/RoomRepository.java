package com.example.main.repositories;

import com.example.main.entities.Doctor;
import com.example.main.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByRoomIdAndIsDeletedFalse(@Param("roomId") Long roomId);

    @Query(value =  "SELECT *" +
            " FROM rooms r" +
            " WHERE r.clinic_id = :clinicId",
            nativeQuery = true)
    List<Room> findByClinicId(@Param("clinicId") Long clinicId);
}
