package br.com.dh.desafio_quality.controller;

import br.com.dh.desafio_quality.dto.PropertyRequestDTO;
import br.com.dh.desafio_quality.dto.PropertyResponseDTO;
import br.com.dh.desafio_quality.enums.Msg;
import br.com.dh.desafio_quality.exception.InvalidParamException;
import br.com.dh.desafio_quality.service.IProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

/**
 * The type Property controller.
 */
@RestController
@RequestMapping("/property")
public class PropertyController {
  @Autowired
  private IProperty service;

  /**
   * Gets property.
   *
   * @param id the id
   * @return the property
   * @throws InvalidParamException the invalid param exception
   */
  @GetMapping("/{id}")
  public ResponseEntity<PropertyResponseDTO> getProperty(@PathVariable String id) throws InvalidParamException {
    try {
      return new ResponseEntity<>(service.getProperty(UUID.fromString(id)), HttpStatus.OK);
    } catch (IllegalArgumentException e) {
      throw new InvalidParamException(Msg.ID_NOT_VALID);
    }
  }

  /**
   * Post property response entity.
   *
   * @param property the property
   * @return the response entity
   */
  @PostMapping
  @ResponseBody
  public ResponseEntity<PropertyResponseDTO> postProperty(@RequestBody @Valid PropertyRequestDTO property) {
    return new ResponseEntity<>(service.postProperty(property), HttpStatus.CREATED);
  }
}
