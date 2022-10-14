package br.com.dh.desafio_quality.service;

import br.com.dh.desafio_quality.dto.PropertyRequestDTO;
import br.com.dh.desafio_quality.dto.PropertyResponseDTO;
import br.com.dh.desafio_quality.exception.NotFoundException;

import javax.validation.Valid;
import java.util.UUID;

public interface IProperty {
  public PropertyResponseDTO getProperty(UUID id) throws NotFoundException;
  public PropertyResponseDTO postProperty(PropertyRequestDTO property);
}
