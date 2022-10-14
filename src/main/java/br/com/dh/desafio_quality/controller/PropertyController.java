package br.com.dh.desafio_quality.controller;

import br.com.dh.desafio_quality.dto.PropertyDTO;
import br.com.dh.desafio_quality.model.Property;
import br.com.dh.desafio_quality.service.IProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/property")
public class PropertyController {
  @Autowired
  private IProperty service;

  @GetMapping("/{id}")
  public ResponseEntity<PropertyDTO> getProperty(@PathVariable UUID id) {
    return new ResponseEntity<>(service.getProperty(id), HttpStatus.OK);
  }

  @PostMapping
  @ResponseBody
  public ResponseEntity<PropertyDTO> postProperty(@RequestBody @Valid Property property) {
    return new ResponseEntity<>(service.postProperty(property), HttpStatus.CREATED);
  }
}
