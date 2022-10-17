package br.com.dh.desafio_quality.dto;

import br.com.dh.desafio_quality.model.Property;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The type Property response dto.
 */
@Getter
public class PropertyResponseDTO {
  private UUID id;
  private String propName;
  private String propDistrict;
  private double propArea;
  private BigDecimal propValue;
  private List<RoomResponseDTO> rooms;
  private RoomResponseDTO largestRoom;

  /**
   * Instantiates a new Property response dto.
   *
   * @param property the property
   */
  public PropertyResponseDTO(Property property) {
    this.id = property.getId();
    this.propName = property.getPropName();
    this.propDistrict = property.getPropDistrict().getName();
    this.propArea = property.getPropArea();
    this.propValue = property.getPropValue();
    this.rooms = property.getRooms().stream().map(RoomResponseDTO::new).collect(Collectors.toList());
    this.largestRoom = new RoomResponseDTO(property.getLargestRoom());
  }
}
