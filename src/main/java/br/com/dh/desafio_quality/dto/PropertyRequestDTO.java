package br.com.dh.desafio_quality.dto;

import br.com.dh.desafio_quality.enums.Msg;
import br.com.dh.desafio_quality.model.District;
import br.com.dh.desafio_quality.model.Room;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Property request dto.
 */
@Getter
@Setter
@NoArgsConstructor
public class PropertyRequestDTO {
    @NotBlank(message = Msg.NAME_NOT_EMPTY)
    @Size(min = 1, max = 30, message = Msg.NAME_SIZE_NOT_VALID)
    @Pattern(regexp = "[A-ZÁÀÃÉÈÊÍÏÓÔÕÖÚÇÑ][a-záàâãéèêíïóôõöúçñ]+.*", message = Msg.NAME_NOT_VALID)
    private String propName;
    @NotNull(message = Msg.DISTRICT_REQUIRED)
//    @Pattern(regexp = "[A-ZÁÀÃÉÈÊÍÏÓÔÕÖÚÇÑ][a-záàâãéèêíïóôõöúçñ]+.*", message = Msg.NAME_NOT_VALID)
    private @Valid DistrictDTO propDistrict;
    @NotNull(message = Msg.ROOM_REQUIRED)
    // @NotBlank(message = Msg.ROOM_NOT_EMPTY)
    private List<@Valid RoomRequestDTO> rooms;

    /**
     * Instantiates a new Property request dto.
     *
     * @param propName     the prop name
     * @param propDistrict the prop district
     * @param rooms        the rooms
     */
    public PropertyRequestDTO(String propName, DistrictDTO propDistrict, List<Room> rooms) {
        this.propName = propName;
        this.propDistrict = new DistrictDTO(propDistrict.getName(), propDistrict.getValueM2());
        this.rooms = rooms.stream().map(RoomRequestDTO::new).collect(Collectors.toList());
    }
}
