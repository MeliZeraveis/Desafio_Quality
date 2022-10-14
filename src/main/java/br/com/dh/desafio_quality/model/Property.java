package br.com.dh.desafio_quality.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Property {
  private UUID id;
  private String propName;
  private District propDistrict;
  private double propArea;
  private BigDecimal propValue;
  private List<Room> rooms;
  private Room largestRoom;

  public Property(String propName, District propDistrict, List<Room> rooms) {
    this.id = UUID.randomUUID();
    this.propName = propName;
    this.propDistrict = propDistrict;
    this.rooms = rooms;
    this.propArea = rooms.stream().mapToDouble(Room::getRoomArea).sum();
    this.propValue = propDistrict.getValueM2().multiply(BigDecimal.valueOf(propArea));
    this.largestRoom = rooms.stream().max((r1, r2) -> Double.compare(r1.getRoomArea(), r2.getRoomArea())).get();
  }
}
