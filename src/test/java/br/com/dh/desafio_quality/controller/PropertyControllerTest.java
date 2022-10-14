package br.com.dh.desafio_quality.controller;

import br.com.dh.desafio_quality.dto.PropertyRequestDTO;
import br.com.dh.desafio_quality.dto.PropertyResponseDTO;
import br.com.dh.desafio_quality.exception.NotFoundException;
import br.com.dh.desafio_quality.model.District;
import br.com.dh.desafio_quality.model.Property;
import br.com.dh.desafio_quality.model.Room;
import br.com.dh.desafio_quality.service.IProperty;
import br.com.dh.desafio_quality.service.PropertyService;
import br.com.dh.desafio_quality.util.TestUtilsGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PropertyController.class)
class PropertyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    PropertyController controller;

    @MockBean
    PropertyService service;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

//    @Test
//    @DisplayName("Tests the method that gets a property by ID - Controller")
//    void getPropertyById_WhenIdIsValid_ThenReturnProperty() throws NotFoundException {
//
//    }

    @Test
    @DisplayName("Tests the method that posts a property - Controller")
    void postProperty_WhenPropertyIsValid_ThenReturnProperty() throws Exception {
        PropertyRequestDTO property = TestUtilsGenerator.postProperty();
        PropertyResponseDTO propertyResponse = TestUtilsGenerator.postPropertyResponse();

//        ResponseEntity<PropertyResponseDTO> response = controller.postProperty(property);

        BDDMockito.when(service.postProperty(ArgumentMatchers.any(PropertyRequestDTO.class))).thenReturn(propertyResponse);

        ResultActions resposta = mockMvc.perform(
                post("/property")
                .contentType(MediaType.APPLICATION_JSON));

        resposta.andExpect(status().isCreated());

    }
}
