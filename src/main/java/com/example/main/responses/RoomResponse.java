package com.example.main.responses;

import com.example.main.entities.Room;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponse {

    @JsonProperty("name")
    private String name;

    @JsonProperty("clinic_name")
    private String clinicName;

    public static RoomResponse fromRoom(Room room){
       return RoomResponse.builder()
               .name(room.getName())
               .clinicName(room.getClinic().getName())
               .build();
    }

}
