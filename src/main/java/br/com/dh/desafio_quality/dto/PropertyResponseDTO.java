package br.com.dh.desafio_quality.dto;

import br.com.dh.desafio_quality.model.Property;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class PropertyResponseDTO {
  private UUID id;
  private String propName;
  private String propDistrict;
  private double propArea;
  private BigDecimal propValue;
  private List<RoomDTO> rooms;

  public PropertyResponseDTO(Property property) {
    this.id = property.getId();
    this.propName = property.getPropName();
    this.propDistrict = property.getPropDistrict().getName();
    this.propArea = property.getPropArea();
    this.propValue = property.getPropValue();
    this.rooms = property.getRooms().stream().map(RoomDTO::new).collect(Collectors.toList());
  }
}
