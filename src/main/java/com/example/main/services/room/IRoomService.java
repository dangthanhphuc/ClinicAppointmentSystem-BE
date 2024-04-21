package com.example.main.services.room;

import com.example.main.dtos.RoomDTO;
import com.example.main.entities.Room;
import com.example.main.exceptions.IdNotFoundException;

import java.util.List;

public interface IRoomService {
    Room createRoom(RoomDTO roomDTO) throws IdNotFoundException;

    Room updateRoom(Long roomId, RoomDTO roomDTO) throws IdNotFoundException;

    void deleteRoom(Long roomId) throws IdNotFoundException;

    Room getRoom(Long roomId) throws IdNotFoundException;

    List<Room> getRooms();

    List<Room> getRoomsByClinicId(Long clinicId) throws IdNotFoundException;
}
