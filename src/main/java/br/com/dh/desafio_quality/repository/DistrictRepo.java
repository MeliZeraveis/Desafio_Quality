package br.com.dh.desafio_quality.repository;

import br.com.dh.desafio_quality.dto.DistrictDTO;
import br.com.dh.desafio_quality.enums.Msg;
import br.com.dh.desafio_quality.model.District;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class DistrictRepo {
  public static final String linkFile = "src/main/resources/districts.json";
  ObjectMapper mapper = new ObjectMapper();

  public List<District> getAll() {
    List<District> districts = null;
    try {
      districts = Arrays.asList(mapper.readValue(new File(linkFile), District[].class));
    } catch (Exception ex) {
      System.out.println(Msg.FILE_READ_ERROR);
    }
    return districts;
  }

  public District getOne(String propDistrict) {
    return getAll().stream()
            .filter(district -> district.getName().equals(propDistrict))
            .findFirst()
            .orElse(null);
  }

  public void save(DistrictDTO newDistrict) {
    ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
    List<District> districts = new ArrayList<>(getAll());

    districts.add(new District(newDistrict.getName(), newDistrict.getValueM2()));
    try {
      writer.writeValue(new File(linkFile), districts);
    } catch (Exception ex) {
      System.out.println(Msg.FILE_WRITE_ERROR);
    }
  }
}
