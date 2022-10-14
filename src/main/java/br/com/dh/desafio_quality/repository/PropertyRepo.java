package br.com.dh.desafio_quality.repository;

import br.com.dh.desafio_quality.dto.DistrictDTO;
import br.com.dh.desafio_quality.enums.Msg;
import br.com.dh.desafio_quality.model.District;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.hibernate.validator.spi.nodenameprovider.Property;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class PropertyRepo {
    public static final String linkFile = "src/main/resources/properties.json";
    ObjectMapper mapper = new ObjectMapper();

    public List<Property> getAll() {
        List<Property> properties = null;
        try {
            properties = Arrays.asList(mapper.readValue(new File(linkFile), Property[].class));
        } catch (Exception ex) {
            System.out.println(Msg.FILE_READ_ERROR);
        }
        return properties;
    }

    public Property getOne(String propProperty) {
        return getAll().stream()
                .filter(property -> property.getName().equals(propProperty))
                .findFirst()
                .orElse(null);
    }

    public void save(PropertyDTO newProperty) {
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        List<Property> properties = new ArrayList<>(getAll());

        properties.add(new Property(newProperty.getName(), newProperty.getValueM2()));
        try {
            writer.writeValue(new File(linkFile), properties);
        } catch (Exception ex) {
            System.out.println(Msg.FILE_WRITE_ERROR);
        }
    }
}
