package br.com.dh.desafio_quality.service;

import br.com.dh.desafio_quality.dto.PropertyDTO;
import br.com.dh.desafio_quality.model.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * The type Property service.
 */
@Service
public class PropertyService implements IProperty {
  @Autowired
  private PropertyRepo repo;
  @Override
  public PropertyDTO getProperty(UUID id) {
    return repo.getOne(id);
  }

  @Override
  public PropertyDTO postProperty(Property property) {
    return repo.save(property);
  }
}
