package br.com.dh.desafio_quality.dto;

import br.com.dh.desafio_quality.enums.Msg;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
public class DistrictDTO {
  @NotBlank(message = Msg.NAME_NOT_EMPTY)
  // `@Pattern(regexp = "[A-ZÁÀÃÉÈÊÍÏÓÔÕÖÚÇÑ][a-záàâãéèêíïóôõöúçñ]+", message = Msg.NAME_NOT_VALID)
  @Size(min = 1, max = 45, message = Msg.NAME_SIZE_NOT_VALID)
  private String name;
  @NotNull(message = Msg.DISTRICT_VALUE_REQUIRED)
  @DecimalMin(value = "0.01", message = Msg.DISTRICT_VALUE_NOT_POSITIVE)
  @Digits(integer=13, fraction=2, message = Msg.DISTRICT_VALUE_NOT_VALID)
  private BigDecimal valueM2;

  public DistrictDTO(String name, BigDecimal valueM2) {
    this.name = name;
    this.valueM2 = valueM2;
  }
}
