package br.com.dh.desafio_quality.util;

import br.com.dh.desafio_quality.dto.PropertyRequestDTO;
import br.com.dh.desafio_quality.dto.PropertyResponseDTO;
import br.com.dh.desafio_quality.dto.RoomDTO;
import br.com.dh.desafio_quality.model.District;
import br.com.dh.desafio_quality.model.Property;
import br.com.dh.desafio_quality.model.Room;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TestUtilsGenerator {
    public static PropertyRequestDTO postProperty() {
        District district = new District("Santa Teresa", new BigDecimal("3000.0"));
        Room room1 = new Room("Living room", 15, 10);
        Room room2 = new Room("Bedroom", 6, 5);
        Room room3 = new Room("Office", 3, 3);

        List<Room> rooms = new ArrayList<>();
        rooms.add(room1);
        rooms.add(room2);
        rooms.add(room3);

        return PropertyRequestDTO.builder()
                .propName("Casa de praia")
                .propDistrict(district)
                .rooms(rooms)
                .build();
    }

    public static PropertyResponseDTO postPropertyResponse() {
        District district = new District("Santa Teresa", new BigDecimal("3000.0"));
        RoomDTO room1 = new RoomDTO(new Room("Living room", 15, 10));
        RoomDTO room2 = new RoomDTO(new Room("Bedroom", 30, 10));
        RoomDTO room3 = new RoomDTO(new Room("Office", 9, 3));

        List<RoomDTO> rooms = new ArrayList<>();
        rooms.add(room1);
        rooms.add(room2);
        rooms.add(room3);

        return PropertyResponseDTO.builder()
                .propName("Casa de praia")
                .propDistrict("Santa Teresa")
                .propArea(180.0)
                .propValue(new BigDecimal("540000.0"))
                .rooms(rooms)
                .build();
    }


}
