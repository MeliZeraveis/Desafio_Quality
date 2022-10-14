package br.com.dh.desafio_quality.dto;

import br.com.dh.desafio_quality.model.District;
import br.com.dh.desafio_quality.model.Room;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class PropertyRequestDTO {
    private String propName;
    private District propDistrict;
    private List<Room> rooms;

    public PropertyRequestDTO(String propName, DistrictDTO propDistrict, List<Room> rooms) {
        this.propName = propName;
        this.propDistrict = new District(propDistrict.getName(), propDistrict.getValueM2());
        this.rooms = rooms.stream()
                .map(room -> new Room(room.getRoomName(), room.getRoomLength(), room.getRoomWidth()))
                .collect(Collectors.toList());
    }
}
