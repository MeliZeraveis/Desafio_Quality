package br.com.dh.desafio_quality.service;

import br.com.dh.desafio_quality.dto.DistrictDTO;
import br.com.dh.desafio_quality.model.District;

import java.util.List;
import java.util.Map;

public interface IDistrict {
  List<District> get(Map<String, String> params);
  void save(DistrictDTO newDistrict);
}
