package br.com.dh.desafio_quality.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Room {
  private String roomName;
  private double roomLength;
  private double roomWidth;
  private double roomArea;

  public Room(String roomName, double roomLength, double roomWidth) {
    this.roomName = roomName;
    this.roomLength = roomLength;
    this.roomWidth = roomWidth;
    this.roomArea = roomLength * roomWidth;
  }
}
