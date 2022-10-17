package br.com.dh.desafio_quality.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * The type District.
 */
@Getter
@Setter
@NoArgsConstructor
public class District {
  private UUID id;
  private String name;
  private BigDecimal valueM2;

  /**
   * Instantiates a new District.
   *
   * @param name    the name
   * @param valueM2 the value m 2
   */
  public District(String name, BigDecimal valueM2) {
    this.id = UUID.randomUUID();
    this.name = name;
    this.valueM2 = valueM2;
  }
}
