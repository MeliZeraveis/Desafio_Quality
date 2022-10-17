package br.com.dh.desafio_quality.dto;

import br.com.dh.desafio_quality.model.Room;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Room response dto.
 */
@Getter
public class RoomResponseDTO {
  private String roomName;
  private double roomArea;

  /**
   * Instantiates a new Room response dto.
   *
   * @param room the room
   */
  public RoomResponseDTO(Room room) {
    this.roomName = room.getRoomName();
    this.roomArea = room.getRoomArea();
  }
}
