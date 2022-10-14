package br.com.dh.desafio_quality.dto;

import br.com.dh.desafio_quality.enums.Msg;
import br.com.dh.desafio_quality.model.District;
import br.com.dh.desafio_quality.model.Room;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class PropertyRequestDTO {
    @NotBlank(message = Msg.NAME_NOT_EMPTY)
    @Size(min = 1, max = 30, message = Msg.NAME_SIZE_NOT_VALID)
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
