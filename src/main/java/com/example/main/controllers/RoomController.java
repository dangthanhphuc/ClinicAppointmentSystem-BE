package com.example.main.controllers;

import com.example.main.dtos.RoomDTO;
import com.example.main.entities.Room;
import com.example.main.entities.Room;
import com.example.main.entities.Room;
import com.example.main.exceptions.IdNotFoundException;
import com.example.main.filters.InputInvalidFilter;
import com.example.main.responses.RoomResponse;
import com.example.main.responses.RoomResponse;
import com.example.main.responses.ResponseObject;
import com.example.main.services.room.IRoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RequestMapping("${api.prefix}/rooms")
@RestController
public class RoomController {
    private final IRoomService roomService;

    @GetMapping("/{roomId}")
    public ResponseEntity<ResponseObject> getRoom(
            @PathVariable Long roomId
    ) throws IdNotFoundException {
        Room room = roomService.getRoom(roomId);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Room get successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(RoomResponse.fromRoom(room))
                        .build()
        );
    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> getRooms() {
        List<Room> room = roomService.getRooms();
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Rooms get successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(room.stream().map(RoomResponse::fromRoom))
                        .build()
        );
    }

    @GetMapping("/clinic/{clinicId}")
    public ResponseEntity<ResponseObject> getRoomsByClinicId(
            @PathVariable Long clinicId
    ) throws IdNotFoundException {
        List<Room> rooms = roomService.getRoomsByClinicId(clinicId);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Rooms get successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(rooms.stream().map(RoomResponse::fromRoom))
                        .build()
        );
    }

    @PreAuthorize("hasRole('ROLE_CLINIC_MANAGER') or hasRole('ROLE_ADMINISTRATOR')")
    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createRoom(
            @Valid @RequestBody RoomDTO roomDTO,
            BindingResult result
    ) throws IdNotFoundException {

        ResponseEntity<ResponseObject> isInvalid = InputInvalidFilter.checkInvalidInput(result);
        if(isInvalid != null) {
            return isInvalid;
        }

        Room room = roomService.createRoom(roomDTO);

        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Room created successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(RoomResponse.fromRoom(room))
                        .build()
        );
    }

    @PreAuthorize("hasRole('ROLE_CLINIC_MANAGER') or hasRole('ROLE_ADMINISTRATOR')")
    @PutMapping("/update/{roomId}")
    public ResponseEntity<ResponseObject> updateRoom(
            @PathVariable Long roomId,
            @Valid @RequestBody RoomDTO roomDTO,
            BindingResult result
    ) throws IdNotFoundException {
        ResponseEntity<ResponseObject> isInvalid = InputInvalidFilter.checkInvalidInput(result);
        if(isInvalid != null) {
            return isInvalid;
        }

        Room Room = roomService
                .updateRoom(roomId, roomDTO);

        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Room updated successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(RoomResponse.fromRoom(Room))
                        .build()
        );
    }

    @PreAuthorize("hasRole('ROLE_CLINIC_MANAGER') or hasRole('ROLE_ADMINISTRATOR')")
    @DeleteMapping("/delete/{roomId}")
    public ResponseEntity<ResponseObject> deleteRoom(
            @PathVariable Long roomId
    ) throws IdNotFoundException {
        roomService.deleteRoom(roomId);

        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Room deleted successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data("Delete successfully !")
                        .build()
        );
    }
}
