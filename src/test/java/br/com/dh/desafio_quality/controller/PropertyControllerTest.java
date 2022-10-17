package br.com.dh.desafio_quality.controller;

import br.com.dh.desafio_quality.dto.DistrictDTO;
import br.com.dh.desafio_quality.dto.PropertyRequestDTO;
import br.com.dh.desafio_quality.dto.PropertyResponseDTO;
import br.com.dh.desafio_quality.enums.Msg;
import br.com.dh.desafio_quality.exception.NotFoundException;
import br.com.dh.desafio_quality.model.Property;
import br.com.dh.desafio_quality.model.Room;
import br.com.dh.desafio_quality.service.PropertyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type Property controller test.
 */
@WebMvcTest(PropertyController.class)
class PropertyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * The Controller.
     */
    PropertyController controller;

    /**
     * The Service.
     */
    @MockBean
    PropertyService service;

    /**
     * The Property request.
     */
    PropertyRequestDTO propertyRequest;
    /**
     * The Property response.
     */
    PropertyResponseDTO propertyResponse;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        List<Room> listRooms = new ArrayList<>();
        listRooms.add(new Room("Sala de estar", 4, 5 ));
        listRooms.add(new Room("Cozinha", 10, 10 ));

        DistrictDTO district = new DistrictDTO("Copacabana", BigDecimal.valueOf(1000) );

        propertyRequest = new PropertyRequestDTO("Casa", district, listRooms);

        List<Room> newRooms = propertyRequest.getRooms().stream()
                .map(room -> new Room(room.getRoomName(), room.getRoomWidth(), room.getRoomLength()))
                .collect(Collectors.toList());

        Property property = new Property(propertyRequest.getPropName(), propertyRequest.getPropDistrict(), newRooms);

        propertyResponse = new PropertyResponseDTO(property);

    }

    /**
     * Gets property by id when id is valid then return property.
     *
     * @throws NotFoundException the not found exception
     * @throws Exception         the exception
     */
    @Test
    @DisplayName("Tests the method that gets a property by ID - Controller")
    void getPropertyById_WhenIdIsValid_ThenReturnProperty() throws NotFoundException, Exception {
        BDDMockito.given(service.getProperty(ArgumentMatchers.any(UUID.class))).willReturn(propertyResponse);

        ResultActions response = mockMvc.perform(
                get("/property/{id}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.propName", CoreMatchers.is(propertyResponse.getPropName())))
                .andExpect(jsonPath("$.propDistrict", CoreMatchers.is(propertyResponse.getPropDistrict())));
    }

    /**
     * Gets property by id when id is valid then return exception.
     *
     * @throws NotFoundException the not found exception
     * @throws Exception         the exception
     */
    @Test
    @DisplayName("Tests the method that gets a property by ID and return an Exception - Controller")
    void getPropertyById_WhenIdIsValid_ThenReturnException() throws NotFoundException, Exception {
        BDDMockito.given(service.getProperty(ArgumentMatchers.any())).willThrow(new NotFoundException(Msg.PROPERTY_NOT_FOUND));

        ResultActions response = mockMvc.perform(
                get("/property/{id}", propertyResponse.getId())
                        .contentType(MediaType.APPLICATION_JSON));


        response.andExpect(status().isNotFound());
    }

    /**
     * Post property when property is valid then return property.
     *
     * @throws Exception the exception
     */
    @Test
    @DisplayName("Tests the method that posts a property - Controller")
    void postProperty_WhenPropertyIsValid_ThenReturnProperty() throws Exception {

        List<Room> listRooms = new ArrayList<>();
        listRooms.add(new Room("Sala de estar", 4, 5 ));
        listRooms.add(new Room("Cozinha", 10, 10 ));
        DistrictDTO district = new DistrictDTO("Copacabana", BigDecimal.valueOf(1000) );

        PropertyRequestDTO propertyRequest = new PropertyRequestDTO("Casa", district, listRooms);

        BDDMockito.when(service.postProperty(ArgumentMatchers.any())).thenReturn(propertyResponse);

        ResultActions resposta = mockMvc.perform(
                post("/property")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(propertyRequest)));

        resposta.andExpect(status().isCreated());
    }
}
