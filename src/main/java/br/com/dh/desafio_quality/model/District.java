package br.com.dh.desafio_quality.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class District {
  private UUID id;
  private String name;
  private BigDecimal valueM2;

  public District(String name, BigDecimal valueM2) {
    this.id = UUID.randomUUID();
    this.name = name;
    this.valueM2 = valueM2;
  }
}
