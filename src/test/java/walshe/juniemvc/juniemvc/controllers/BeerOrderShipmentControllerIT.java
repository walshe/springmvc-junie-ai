package walshe.juniemvc.juniemvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import walshe.juniemvc.juniemvc.entities.BeerOrder;
import walshe.juniemvc.juniemvc.models.CreateBeerOrderShipmentCommand;
import walshe.juniemvc.juniemvc.repositories.BeerOrderRepository;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class BeerOrderShipmentControllerIT {

    @Autowired
    WebApplicationContext wac;

    @Autowired
    BeerOrderRepository beerOrderRepository;

    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        objectMapper.findAndRegisterModules();
    }

    @Test
    void testCreateShipment() throws Exception {
        BeerOrder order = beerOrderRepository.save(BeerOrder.builder().customerRef("IT-REF").build());

        CreateBeerOrderShipmentCommand command = new CreateBeerOrderShipmentCommand(
                order.getId(), LocalDateTime.now(), "IT-Carrier", "IT-TRACK");

        mockMvc.perform(post("/api/v1/shipments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.carrier").value("IT-Carrier"));
    }

    @Test
    void testListShipments() throws Exception {
        mockMvc.perform(get("/api/v1/shipments")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
