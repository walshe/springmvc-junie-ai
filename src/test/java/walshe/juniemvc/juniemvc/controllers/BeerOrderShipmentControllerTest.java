package walshe.juniemvc.juniemvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import walshe.juniemvc.juniemvc.models.BeerOrderShipmentDto;
import walshe.juniemvc.juniemvc.models.CreateBeerOrderShipmentCommand;
import walshe.juniemvc.juniemvc.models.UpdateBeerOrderShipmentCommand;
import walshe.juniemvc.juniemvc.services.BeerOrderShipmentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BeerOrderShipmentControllerTest {

    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    BeerOrderShipmentService beerOrderShipmentService;

    BeerOrderShipmentDto shipmentDto;

    @BeforeEach
    void setUp() {
        objectMapper.findAndRegisterModules();
        beerOrderShipmentService = mock(BeerOrderShipmentService.class);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new BeerOrderShipmentController(beerOrderShipmentService)).build();

        shipmentDto = BeerOrderShipmentDto.builder()
                .id(1)
                .beerOrderId(1)
                .carrier("Test Carrier")
                .trackingNumber("Test Track")
                .build();
    }

    @Test
    void listShipments() throws Exception {
        given(beerOrderShipmentService.listShipments()).willReturn(List.of(shipmentDto));

        mockMvc.perform(get("/api/v1/shipments")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(1)));
    }

    @Test
    void getShipmentById() throws Exception {
        given(beerOrderShipmentService.getShipmentById(1)).willReturn(Optional.of(shipmentDto));

        mockMvc.perform(get("/api/v1/shipments/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.carrier", is("Test Carrier")));
    }

    @Test
    void getShipmentByIdNotFound() throws Exception {
        given(beerOrderShipmentService.getShipmentById(any())).willReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/shipments/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createShipment() throws Exception {
        CreateBeerOrderShipmentCommand command = new CreateBeerOrderShipmentCommand(1, LocalDateTime.now(), "Carrier", "Track");
        given(beerOrderShipmentService.createShipment(any())).willReturn(shipmentDto);

        mockMvc.perform(post("/api/v1/shipments")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void updateShipment() throws Exception {
        UpdateBeerOrderShipmentCommand command = new UpdateBeerOrderShipmentCommand(LocalDateTime.now(), "Updated", "UpdatedTrack");
        given(beerOrderShipmentService.updateShipment(any(), any())).willReturn(Optional.of(shipmentDto));

        mockMvc.perform(put("/api/v1/shipments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isNoContent());

        verify(beerOrderShipmentService).updateShipment(any(), any());
    }

    @Test
    void deleteShipment() throws Exception {
        given(beerOrderShipmentService.deleteShipment(1)).willReturn(true);

        mockMvc.perform(delete("/api/v1/shipments/1"))
                .andExpect(status().isNoContent());

        verify(beerOrderShipmentService).deleteShipment(1);
    }
}
