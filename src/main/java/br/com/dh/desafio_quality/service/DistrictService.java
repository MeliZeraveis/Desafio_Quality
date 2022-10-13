package br.com.dh.desafio_quality.service;

import br.com.dh.desafio_quality.dto.DistrictDTO;
import br.com.dh.desafio_quality.enums.Msg;
import br.com.dh.desafio_quality.model.District;
import br.com.dh.desafio_quality.repository.DistrictRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DistrictService implements IDistrict {
  @Autowired
  public DistrictRepo repo;

  @Override
  public List<District> get(Map<String, String> params) {
    if (params.containsKey("propDistrict")) {
      List<District> districts = new ArrayList<>();
      District district = repo.getOne(params.get("propDistrict"));
      if (district == null) {
        throw new NoSuchElementException(Msg.DISTRICT_NOT_FOUND);
      }
      districts.add(district);
      return districts;
    }

    return repo.getAll();
  }

  @Override
  public void save(DistrictDTO newDistrict) {
    repo.save(newDistrict);
  }
}
