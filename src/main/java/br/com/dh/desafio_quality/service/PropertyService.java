package br.com.dh.desafio_quality.service;

import br.com.dh.desafio_quality.dto.PropertyRequestDTO;
import br.com.dh.desafio_quality.dto.PropertyResponseDTO;
import br.com.dh.desafio_quality.enums.Msg;
import br.com.dh.desafio_quality.exception.NotFoundException;
import br.com.dh.desafio_quality.model.Property;
import br.com.dh.desafio_quality.repository.PropertyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

/**
 * The type Property service.
 */
@Service
public class PropertyService implements IProperty {
  @Autowired
  private PropertyRepo repo;
  @Override
  public PropertyResponseDTO getProperty(UUID id) throws NotFoundException {
    Optional<Property> property = repo.getOne(id);

    if (property.isEmpty()) {
      throw new NotFoundException(Msg.PROPERTY_NOT_FOUND);
    }

    return new PropertyResponseDTO(property.get());
  }

  @Override
  public PropertyResponseDTO postProperty(PropertyRequestDTO newProperty) {
    return repo.save(new Property(newProperty.getPropName(), newProperty.getPropDistrict(), newProperty.getRooms()));
  }
}
