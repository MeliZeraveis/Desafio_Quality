package br.com.dh.desafio_quality.controller;

import br.com.dh.desafio_quality.dto.DistrictDTO;
import br.com.dh.desafio_quality.model.District;
import br.com.dh.desafio_quality.service.IDistrict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/district")
public class DistrictController {
  @Autowired
  private IDistrict service;

  @GetMapping
  public ResponseEntity<List<District>> get(@RequestParam Map<String,String> params) {
    return new ResponseEntity<>(service.get(params), HttpStatus.OK);
  }

  @PostMapping
  @ResponseBody
  public ResponseEntity<Void> save(@RequestBody @Valid DistrictDTO newDistrict) {
    service.save(newDistrict);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }
}
