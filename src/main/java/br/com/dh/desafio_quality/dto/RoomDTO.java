package br.com.dh.desafio_quality.dto;

import br.com.dh.desafio_quality.enums.Msg;
import br.com.dh.desafio_quality.model.Room;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class RoomDTO {
  @NotBlank(message = Msg.ROOM_NAME_NOT_EMPTY)
  @Size(min = 1, max = 30, message = Msg.ROOM_NAME_SIZE)
  private String roomName;
  private double roomArea;

  public RoomDTO(Room room) {
    this.roomName = room.getRoomName();
    this.roomArea = room.getRoomArea();
  }
}
