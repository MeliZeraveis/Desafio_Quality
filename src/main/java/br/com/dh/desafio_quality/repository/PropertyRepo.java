package br.com.dh.desafio_quality.repository;

import br.com.dh.desafio_quality.dto.PropertyRequestDTO;
import br.com.dh.desafio_quality.dto.PropertyResponseDTO;
import br.com.dh.desafio_quality.enums.Msg;
import br.com.dh.desafio_quality.model.Property;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.*;

/**
 * The type Property repo.
 */
@Repository
public class PropertyRepo {
    /**
     * The constant linkFile.
     */
    public static final String linkFile = "src/main/resources/properties.json";
    /**
     * The Mapper.
     */
    ObjectMapper mapper = new ObjectMapper();

    /**
     * Gets all.
     *
     * @return the all
     */
    public List<Property> getAll() {
        List<Property> properties = null;
        try {
            properties = Arrays.asList(mapper.readValue(new File(linkFile), Property[].class));
        } catch (Exception ex) {
            System.out.println(Msg.FILE_READ_ERROR);
        }
        return properties;
    }

    /**
     * Gets one.
     *
     * @param id the id
     * @return the one
     */
    public Optional<Property> getOne(UUID id) {
        List<Property> properties = getAll();

        for (Property p : properties) {
            if (p.getId().equals(id)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    /**
     * Save property response dto.
     *
     * @param newProperty the new property
     * @return the property response dto
     */
    public PropertyResponseDTO save(Property newProperty) {
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        List<Property> properties = new ArrayList<>(getAll());

        properties.add(newProperty);

        try {
            writer.writeValue(new File(linkFile), properties);
        } catch (Exception ex) {
            System.out.println(Msg.FILE_WRITE_ERROR);
        }

        return new PropertyResponseDTO(newProperty);
    }

    /**
     * Update.
     *
     * @param listProperty the list property
     */
    public  void update(List<Property> listProperty){
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());

        try {
            writer.writeValue(new File(linkFile), listProperty);
        } catch (Exception ex) {
            System.out.println(Msg.FILE_WRITE_ERROR);
        }
    }

    /**
     * Delete all.
     */
    public void deleteAll(){
        List<Property> emptyArray = new ArrayList<>();
        update(emptyArray);
    }
}
