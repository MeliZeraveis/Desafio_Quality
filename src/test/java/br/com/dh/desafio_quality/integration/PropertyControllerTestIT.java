package br.com.dh.desafio_quality.integration;

import br.com.dh.desafio_quality.dto.DistrictDTO;
import br.com.dh.desafio_quality.dto.PropertyRequestDTO;
import br.com.dh.desafio_quality.enums.ExceptionType;
import br.com.dh.desafio_quality.enums.Msg;
import br.com.dh.desafio_quality.exception.ExceptionDetails;
import br.com.dh.desafio_quality.model.Property;
import br.com.dh.desafio_quality.model.Room;
import br.com.dh.desafio_quality.repository.PropertyRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
//import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
@SpringBootTest
@AutoConfigureMockMvc
public class PropertyControllerTestIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PropertyRepo repository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        repository.deleteAll();
        List<Room> listRooms = new ArrayList<>();
        listRooms.add(new Room("quarto", 4, 5 ));
        listRooms.add(new Room("cozinha", 10, 10 ));
        DistrictDTO district = new DistrictDTO("Tijuca", BigDecimal.valueOf(1000) );
        PropertyRequestDTO propertyRequestBefore = new PropertyRequestDTO("Casa", district, listRooms);
        List<Room> listNewRooms = propertyRequestBefore.getRooms().stream().map(room -> new Room(room.getRoomName(), room.getRoomWidth(), room.getRoomLength()))
                .collect(Collectors.toList());


        repository.save(new Property(propertyRequestBefore.getPropName(), propertyRequestBefore.getPropDistrict(), listNewRooms));
    }


    @Test
    void newProperty_ReturnProperty_Success() throws Exception {
        List<Room> listRooms = new ArrayList<>();
        listRooms.add(new Room("Sala de estar", 4, 5 ));
        listRooms.add(new Room("Cozinha", 10, 10 ));

        DistrictDTO district = new DistrictDTO("Copacabana", BigDecimal.valueOf(1000) );

        PropertyRequestDTO propertyRequest = new PropertyRequestDTO("Casa", district, listRooms);

        ResultActions response = mockMvc
                .perform(post("/property")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(propertyRequest)));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.propName", CoreMatchers.is(propertyRequest.getPropName())))
                .andExpect(jsonPath("$.propDistrict", CoreMatchers
                        .is(propertyRequest.getPropDistrict().getName())))
                .andExpect(jsonPath("$.propArea", CoreMatchers.is(120.0)))
                .andExpect(jsonPath("$.propValue", CoreMatchers.is(120000.0)));

        assertThat(repository.getAll().size()).isEqualTo(2);
        assertThat(repository.getAll().get(0)).isNotNull();
    }

    @Test
    void newProperty_ReturnException_PropNameNotBlank() throws Exception {
        List<Room> listRooms = new ArrayList<>();
        listRooms.add(new Room("Sala de estar", 4, 5 ));
        listRooms.add(new Room("Cozinha", 10, 10 ));

        DistrictDTO district = new DistrictDTO("Copacabana", BigDecimal.valueOf(1000) );

        PropertyRequestDTO propertyRequest = new PropertyRequestDTO("", district, listRooms);

        ResultActions response = mockMvc
                .perform(post("/property")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(propertyRequest)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is("Parâmetros inválidos")))
                .andExpect(jsonPath("$.message", CoreMatchers.is("Os campos estão inválidos")))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("propName")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.NAME_NOT_EMPTY)));
    }

    @Test
    void newProperty_ReturnException_propNameLength() throws Exception {
        List<Room> listRooms = new ArrayList<>();
        listRooms.add(new Room("Sala de estar", 4, 5 ));
        listRooms.add(new Room("Cozinha", 10, 10 ));

        DistrictDTO district = new DistrictDTO("Copacabana", BigDecimal.valueOf(1000) );

        PropertyRequestDTO propertyRequest = new PropertyRequestDTO("AaaaaaaaaAAAAAAAAaaaaaaaaAAAAAAA", district, listRooms);

        ResultActions response = mockMvc
                .perform(post("/property")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(propertyRequest)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is("Parâmetros inválidos")))
                .andExpect(jsonPath("$.message", CoreMatchers.is("Os campos estão inválidos")))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("propName")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.NAME_SIZE_NOT_VALID)));
    }

    @Test
    void newProperty_ReturnException_propNameLowerCase() throws Exception {
        List<Room> listRooms = new ArrayList<>();
        listRooms.add(new Room("Sala de estar", 4, 5 ));
        listRooms.add(new Room("Cozinha", 10, 10 ));

        DistrictDTO district = new DistrictDTO("Copacabana", BigDecimal.valueOf(1000) );

        PropertyRequestDTO propertyRequest = new PropertyRequestDTO("a", district, listRooms);

        ResultActions response = mockMvc
                .perform(post("/property")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(propertyRequest)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is("Parâmetros inválidos")))
                .andExpect(jsonPath("$.message", CoreMatchers.is("Os campos estão inválidos")))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("propName")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.NAME_NOT_VALID)));
    }

    @Test
    void newProperty_ReturnException_roomNameNotBlank() throws Exception {
        List<Room> listRooms = new ArrayList<>();
        listRooms.add(new Room("", 4, 5 ));
        listRooms.add(new Room("Cozinha", 10, 10 ));

        DistrictDTO district = new DistrictDTO("Copacabana", BigDecimal.valueOf(1000) );

        PropertyRequestDTO propertyRequest = new PropertyRequestDTO("Casa do Hugo", district, listRooms);

        ResultActions response = mockMvc
                .perform(post("/property")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(propertyRequest)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is("Parâmetros inválidos")))
                .andExpect(jsonPath("$.message", CoreMatchers.is("Os campos estão inválidos")))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("roomName")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.ROOM_NAME_NOT_EMPTY)));
    }

    @Test
    void newProperty_ReturnException_roomNameLength() throws Exception {
        List<Room> listRooms = new ArrayList<>();
        listRooms.add(new Room("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 4, 5 ));
        listRooms.add(new Room("Cozinha", 10, 10 ));

        DistrictDTO district = new DistrictDTO("Copacabana", BigDecimal.valueOf(1000) );

        PropertyRequestDTO propertyRequest = new PropertyRequestDTO("Casa do Hugo", district, listRooms);

        ResultActions response = mockMvc
                .perform(post("/property")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(propertyRequest)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is("Parâmetros inválidos")))
                .andExpect(jsonPath("$.message", CoreMatchers.is("Os campos estão inválidos")))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("roomName")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.ROOM_NAME_SIZE_NOT_VALID)));
    }

    @Test
    void newProperty_ReturnException_roomLengthNotValid() throws Exception {
        List<Room> listRooms = new ArrayList<>();
        listRooms.add(new Room("Sala de Estar", 55, 5 ));
        listRooms.add(new Room("Cozinha", 10, 10 ));

        DistrictDTO district = new DistrictDTO("Copacabana", BigDecimal.valueOf(1000) );

        PropertyRequestDTO propertyRequest = new PropertyRequestDTO("Casa do Hugo", district, listRooms);

        ResultActions response = mockMvc
                .perform(post("/property")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(propertyRequest)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is("Parâmetros inválidos")))
                .andExpect(jsonPath("$.message", CoreMatchers.is("Os campos estão inválidos")))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("roomLength")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.ROOM_LENGTH_VALUE_NOT_VALID)));
    }

    @Test
    void newProperty_ReturnException_roomWidthNotValid() throws Exception {
        List<Room> listRooms = new ArrayList<>();
        listRooms.add(new Room("Sala de Estar", 10, 5 ));
        listRooms.add(new Room("Cozinha", 10, 80 ));

        DistrictDTO district = new DistrictDTO("Copacabana", BigDecimal.valueOf(1000) );

        PropertyRequestDTO propertyRequest = new PropertyRequestDTO("Casa do Hugo", district, listRooms);

        ResultActions response = mockMvc
                .perform(post("/property")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(propertyRequest)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is("Parâmetros inválidos")))
                .andExpect(jsonPath("$.message", CoreMatchers.is("Os campos estão inválidos")))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("roomWidth")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.ROOM_WIDTH_VALUE_NOT_VALID)));
    }

//    @Test
//    void newProperty_ReturnException_whenRoomListisNull() throws Exception {
//
//        DistrictDTO district = new DistrictDTO("Copacabana", BigDecimal.valueOf(1000) );
//        List<Room> listRooms = new ArrayList<>();
//        listRooms.add(new Room("Sala de Estar", 10, 5 ));
//        listRooms.add(new Room("Cozinha", 10, 80 ));
//        PropertyRequestDTO propertyRequest = new PropertyRequestDTO("Casa do Hugo", district, listRooms);
//
//        ResultActions response = mockMvc
//                .perform(post("/property")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(propertyRequest)));
//
//        response.andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.title", CoreMatchers.is("Parâmetros inválidos")))
//                .andExpect(jsonPath("$.message", CoreMatchers.is("Os campos estão inválidos")))
//                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("rooms")))
//                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.ROOM_REQUIRED)));
//    }

    @Test
    void newProperty_ReturnException_whenDistrictNameIsEmpty() throws Exception {
        List<Room> listRooms = new ArrayList<>();
        listRooms.add(new Room("Sala de Estar", 10, 5 ));
        listRooms.add(new Room("Cozinha", 10, 5 ));

        DistrictDTO district = new DistrictDTO("", BigDecimal.valueOf(1000) );

        PropertyRequestDTO propertyRequest = new PropertyRequestDTO("Casa do Hugo", district, listRooms);

        ResultActions response = mockMvc
                .perform(post("/property")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(propertyRequest)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is("Parâmetros inválidos")))
                .andExpect(jsonPath("$.message", CoreMatchers.is("Os campos estão inválidos")))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("propDistrict.name")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.DISTRICT_NOT_EMPTY)));
    }

    @Test
    void newProperty_ReturnException_whenDistrictNameNotStartWithCapitalLetter() throws Exception {
        List<Room> listRooms = new ArrayList<>();
        listRooms.add(new Room("Sala de Estar", 10, 5 ));
        listRooms.add(new Room("Cozinha", 10, 5 ));

        DistrictDTO district = new DistrictDTO("copacabana", BigDecimal.valueOf(1000) );

        PropertyRequestDTO propertyRequest = new PropertyRequestDTO("Casa do Hugo", district, listRooms);

        ResultActions response = mockMvc
                .perform(post("/property")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(propertyRequest)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is("Parâmetros inválidos")))
                .andExpect(jsonPath("$.message", CoreMatchers.is("Os campos estão inválidos")))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("propDistrict.name")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.NAME_NOT_VALID)));
    }

    @Test
    void newProperty_ReturnException_whenDistrictNameSizeIsNotValid() throws Exception {
        List<Room> listRooms = new ArrayList<>();
        listRooms.add(new Room("Sala de Estar", 10, 5 ));
        listRooms.add(new Room("Cozinha", 10, 5 ));

        DistrictDTO district = new DistrictDTO("copacabanacopacabanacopacabanacopacabanacopacabanacopacabanacopacabanacopacabanacopacabana", BigDecimal.valueOf(1000) );

        PropertyRequestDTO propertyRequest = new PropertyRequestDTO("Casa do Hugo", district, listRooms);

        ResultActions response = mockMvc
                .perform(post("/property")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(propertyRequest)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is("Parâmetros inválidos")))
                .andExpect(jsonPath("$.message", CoreMatchers.is("Os campos estão inválidos")))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("propDistrict.name")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.NAME_SIZE_NOT_VALID)));
    }

    @Test
    void newProperty_ReturnException_whenDistrictValueIsNotPositive() throws Exception {
        List<Room> listRooms = new ArrayList<>();
        listRooms.add(new Room("Sala de Estar", 10, 5 ));
        listRooms.add(new Room("Cozinha", 10, 5 ));

        DistrictDTO district = new DistrictDTO("copacabana", BigDecimal.valueOf(0) );

        PropertyRequestDTO propertyRequest = new PropertyRequestDTO("Casa do Hugo", district, listRooms);

        ResultActions response = mockMvc
                .perform(post("/property")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(propertyRequest)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is("Parâmetros inválidos")))
                .andExpect(jsonPath("$.message", CoreMatchers.is("Os campos estão inválidos")))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("propDistrict.valueM2")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.DISTRICT_VALUE_NOT_POSITIVE)));
    }

    @Test
    void newProperty_ReturnException_whenDistrictValueIsNotValid() throws Exception {
        List<Room> listRooms = new ArrayList<>();
        listRooms.add(new Room("Sala de Estar", 10, 5 ));
        listRooms.add(new Room("Cozinha", 10, 5 ));

        DistrictDTO district = new DistrictDTO("copacabana", BigDecimal.valueOf(100000000000000L) );

        PropertyRequestDTO propertyRequest = new PropertyRequestDTO("Casa do Hugo", district, listRooms);

        ResultActions response = mockMvc
                .perform(post("/property")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(propertyRequest)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is("Parâmetros inválidos")))
                .andExpect(jsonPath("$.message", CoreMatchers.is("Os campos estão inválidos")))
                .andExpect(jsonPath("$.fields", CoreMatchers.containsString("propDistrict.valueM2")))
                .andExpect(jsonPath("$.fieldsMessages", CoreMatchers.containsString(Msg.DISTRICT_VALUE_NOT_VALID)));
    }


    @Test
    void getProperty_returnProperty_Success() throws Exception {
        Property property = repository.getAll().get(0);

        ResultActions response = mockMvc
                .perform(get("/property/{id}", property.getId() )
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.is(property.getId().toString())))
                .andExpect(jsonPath("$.propName", CoreMatchers.is(property.getPropName())))
                .andExpect(jsonPath("$.propArea", CoreMatchers.is(120.0)))
                .andExpect(jsonPath("$.propValue", CoreMatchers.is(120000.0)));

    }

    @Test
    void getProperty_returnException_NotFound() throws Exception {
        ResultActions response = mockMvc
                .perform(get("/property/{id}", "3275b207-4711-4801-9e29-1bd09ae71faf" )
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.OBJECT_NOT_FOUND.message)))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.PROPERTY_NOT_FOUND)))
                .andExpect(jsonPath("$.status", CoreMatchers.is(HttpStatus.NOT_FOUND.value())));
    }

    @Test
    void getProperty_returnException_IdInvalid() throws Exception {
        ResultActions response = mockMvc
                .perform(get("/property/{id}", "aaaaaaa" )
                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", CoreMatchers.is(ExceptionType.PARAMETER_NOT_VALID.message)))
                .andExpect(jsonPath("$.message", CoreMatchers.is(Msg.ID_NOT_VALID)))
                .andExpect(jsonPath("$.status", CoreMatchers.is(HttpStatus.BAD_REQUEST.value())));
    }
}
