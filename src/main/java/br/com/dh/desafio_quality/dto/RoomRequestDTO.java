package br.com.dh.desafio_quality.dto;

import br.com.dh.desafio_quality.enums.Msg;
import br.com.dh.desafio_quality.model.Room;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * The type Room request dto.
 */
@Getter
@Setter
@NoArgsConstructor
public class RoomRequestDTO {
    @NotNull(message = Msg.ROOM_NAME_REQUIRED)
    @NotBlank(message = Msg.ROOM_NAME_NOT_EMPTY)
    @Size(min = 1, max = 30, message = Msg.ROOM_NAME_SIZE_NOT_VALID)
    private String roomName;
    @NotNull(message = Msg.ROOM_LENGTH_REQUIRED)
    @Min(value = 1, message = Msg.ROOM_LENGTH_VALUE_NOT_VALID)
    @Max(value = 33, message = Msg.ROOM_LENGTH_VALUE_NOT_VALID)
    private double roomLength;
    @NotNull(message = Msg.ROOM_WIDTH_REQUIRED)
    @Min(value = 1, message = Msg.ROOM_WIDTH_VALUE_NOT_VALID)
    @Max(value = 33, message = Msg.ROOM_WIDTH_VALUE_NOT_VALID)
    private double roomWidth;

    /**
     * Instantiates a new Room request dto.
     *
     * @param room the room
     */
    public RoomRequestDTO(Room room) {
        this.roomName = room.getRoomName();
        this.roomLength = room.getRoomLength();
        this.roomWidth = room.getRoomWidth();
    }
}
