package br.com.dh.desafio_quality.dto;

import br.com.dh.desafio_quality.model.Room;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoomResponseDTO {
  private String roomName;
  private double roomArea;

  public RoomResponseDTO(Room room) {
    this.roomName = room.getRoomName();
    this.roomArea = room.getRoomArea();
  }
}
