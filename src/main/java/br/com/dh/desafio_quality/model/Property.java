package br.com.dh.desafio_quality.model;

import br.com.dh.desafio_quality.dto.DistrictDTO;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * The type Property.
 */
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

  /**
   * Instantiates a new Property.
   *
   * @param propName     the prop name
   * @param propDistrict the prop district
   * @param rooms        the rooms
   */
  public Property(String propName, DistrictDTO propDistrict, List<Room> rooms) {
    this.id = UUID.randomUUID();
    this.propName = propName;
    this.propDistrict = new District(propDistrict.getName(), propDistrict.getValueM2());
    this.rooms = rooms;
    this.propArea = rooms.stream().mapToDouble(Room::getRoomArea).sum();
    this.propValue = propDistrict.getValueM2().multiply(BigDecimal.valueOf(propArea));
    this.largestRoom = rooms.stream().max((r1, r2) -> Double.compare(r1.getRoomArea(), r2.getRoomArea())).get();
  }
}
