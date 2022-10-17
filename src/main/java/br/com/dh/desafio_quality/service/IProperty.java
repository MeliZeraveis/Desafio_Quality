package br.com.dh.desafio_quality.service;

import br.com.dh.desafio_quality.dto.PropertyRequestDTO;
import br.com.dh.desafio_quality.dto.PropertyResponseDTO;
import br.com.dh.desafio_quality.exception.NotFoundException;

import javax.validation.Valid;
import java.util.UUID;

/**
 * The interface Property.
 */
public interface IProperty {
  /**
   * Gets property.
   *
   * @param id the id
   * @return the property
   * @throws NotFoundException the not found exception
   */
  public PropertyResponseDTO getProperty(UUID id) throws NotFoundException;

  /**
   * Post property property response dto.
   *
   * @param property the property
   * @return the property response dto
   */
  public PropertyResponseDTO postProperty(PropertyRequestDTO property);
}
