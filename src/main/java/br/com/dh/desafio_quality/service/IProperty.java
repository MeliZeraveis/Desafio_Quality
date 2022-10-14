package br.com.dh.desafio_quality.service;

import br.com.dh.desafio_quality.dto.PropertyDTO;
import br.com.dh.desafio_quality.model.Property;

import java.util.UUID;

public interface IProperty {
  public PropertyDTO getProperty(UUID id);
  public PropertyDTO postProperty(Property property);
}
