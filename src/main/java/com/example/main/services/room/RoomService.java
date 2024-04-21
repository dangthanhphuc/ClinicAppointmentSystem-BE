package com.example.main.services.room;

import com.example.main.dtos.RoomDTO;
import com.example.main.entities.Clinic;
import com.example.main.entities.Room;
import com.example.main.exceptions.IdNotFoundException;
import com.example.main.repositories.ClinicRepository;
import com.example.main.repositories.RoomRepository;
import com.example.main.responses.ClinicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RoomService implements IRoomService{
    private final ClinicRepository clinicRepository;
    private final RoomRepository roomRepository;
    @Override
    public Room createRoom(RoomDTO roomDTO) throws IdNotFoundException {
        Clinic existingClinic = clinicRepository
                .findByClinicIdAndIsDeletedFalse(roomDTO.getClinicId())
                .orElseThrow(
                        () -> new IdNotFoundException("Clinic id " + roomDTO.getClinicId() + " is not found")
                );
        Room room = Room.builder()
                .name(roomDTO.getName())
                .clinic(existingClinic)
                .build();

        return roomRepository.save(room);
    }

    @Override
    public Room updateRoom(Long roomId, RoomDTO roomDTO) throws IdNotFoundException {
        Room existingRoom = roomRepository
                .findByRoomIdAndIsDeletedFalse(roomId)
                .orElseThrow(
                        () -> new IdNotFoundException("Room id " + roomId + " is not found")
                );
        Clinic existingClinic = clinicRepository
                .findByClinicIdAndIsDeletedFalse(roomDTO.getClinicId())
                .orElseThrow(
                        () -> new IdNotFoundException("Clinic id " + roomDTO.getClinicId() + " is not found")
                );

        existingRoom.setName(roomDTO.getName());
        existingRoom.setClinic(existingClinic);
        return roomRepository.save(existingRoom);
    }

    @Override
    public void deleteRoom(Long roomId) throws IdNotFoundException {
        Room existingRoom = roomRepository
                .findByRoomIdAndIsDeletedFalse(roomId)
                .orElseThrow(
                        () -> new IdNotFoundException("Clinic id " + roomId + " is not found")
                );
        existingRoom.setDeleted(true);
        roomRepository.save(existingRoom);
    }

    @Override
    public Room getRoom(Long roomId) throws IdNotFoundException {
        return roomRepository
                .findByRoomIdAndIsDeletedFalse(roomId)
                .orElseThrow(
                        () -> new IdNotFoundException("Clinic id " + roomId + " is not found")
                );
    }

    @Override
    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    @Override
    public List<Room> getRoomsByClinicId(Long clinicId) throws IdNotFoundException {
        return roomRepository.findByClinicId(clinicId);
    }
}
