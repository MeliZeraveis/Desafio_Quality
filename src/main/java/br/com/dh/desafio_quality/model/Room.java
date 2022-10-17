package br.com.dh.desafio_quality.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Room.
 */
@Getter
@Setter
@NoArgsConstructor
public class Room {
  private String roomName;
  private double roomLength;
  private double roomWidth;
  private double roomArea;

  /**
   * Instantiates a new Room.
   *
   * @param roomName   the room name
   * @param roomLength the room length
   * @param roomWidth  the room width
   */
  public Room(String roomName, double roomLength, double roomWidth) {
    this.roomName = roomName;
    this.roomLength = roomLength;
    this.roomWidth = roomWidth;
    this.roomArea = roomLength * roomWidth;
  }
}
