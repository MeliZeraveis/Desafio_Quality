package br.com.dh.desafio_quality.service;

import br.com.dh.desafio_quality.dto.DistrictDTO;
import br.com.dh.desafio_quality.dto.PropertyRequestDTO;
import br.com.dh.desafio_quality.dto.PropertyResponseDTO;
import br.com.dh.desafio_quality.enums.Msg;
import br.com.dh.desafio_quality.exception.NotFoundException;
import br.com.dh.desafio_quality.model.Property;
import br.com.dh.desafio_quality.model.Room;
import br.com.dh.desafio_quality.repository.PropertyRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PropertyServiceTest {

    @InjectMocks
    private PropertyService service;

    @Mock
    private PropertyRepo repo;

    private Property property;

    private PropertyResponseDTO propertyResponse;

    private PropertyRequestDTO propertyRequest;


    @BeforeEach
    void setup() {
        List<Room> listRooms = new ArrayList<>();
        listRooms.add(new Room("Sala de estar", 4, 5 ));
        listRooms.add(new Room("Cozinha", 10, 10 ));

        DistrictDTO district = new DistrictDTO("Copacabana", BigDecimal.valueOf(1000) );

        propertyRequest = new PropertyRequestDTO("Casa", district, listRooms);

        List<Room> newList = propertyRequest.getRooms().stream()
                .map(room -> new Room(room.getRoomName(), room.getRoomWidth(), room.getRoomLength()))
                .collect(Collectors.toList());

        property = new Property(propertyRequest.getPropName(), propertyRequest.getPropDistrict(), newList);

        propertyResponse =  new PropertyResponseDTO(property);

    }

    @Test
    @DisplayName("Validando Propriedade existente")
    void getProperty_returnProperty_whenSuccess() {
        Mockito.when(repo.getOne(ArgumentMatchers.any()))
                .thenReturn(Optional.ofNullable(property));

        PropertyResponseDTO propertyTest = service.getProperty(property.getId());

        assertThat(propertyTest).isNotNull();
        assertThat(propertyTest.getPropName()).isEqualTo(property.getPropName());
        assertThat(propertyTest.getPropValue()).isPositive();
    }

    @Test
    @DisplayName("Validando Propriedade inexistente")
    void getProperty_returnException_whenNotSuccess() {
        UUID uuidInexistente = UUID.fromString("e672ed25-8351-4d26-b5bd-e6fe424e6495");

        BDDMockito.given(repo.getOne(ArgumentMatchers.any())).willThrow(new NotFoundException(Msg.PROPERTY_NOT_FOUND));

        assertThrows(NotFoundException.class, () -> {
            service.getProperty(uuidInexistente);
        });
    }

    @Test
    void postProperty_returnCreated_whenPropertyDetailsIsValid() {
        Mockito.when(repo.save(ArgumentMatchers.any()))
                .thenReturn(propertyResponse);

        PropertyResponseDTO propertyTest = service.postProperty(propertyRequest);

        assertThat(propertyTest.getPropValue()).isPositive();
        assertThat(propertyTest.getPropName()).isEqualTo(propertyResponse.getPropName());
        assertThat(propertyTest.getRooms().get(0).getRoomArea()).isPositive();
    }
}
