package walshe.juniemvc.juniemvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import walshe.juniemvc.juniemvc.entities.Beer;
import walshe.juniemvc.juniemvc.repositories.BeerRepository;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class BeerControllerTest {

    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeEach
    void setupMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testListBeers() throws Exception {
        mockMvc.perform(get("/api/v1/beer")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetBeerById() throws Exception {
        Beer saved = beerRepository.save(Beer.builder()
                .beerName("Test Beer")
                .beerStyle("PALE_ALE")
                .upc("123")
                .build());

        mockMvc.perform(get("/api/v1/beer/" + saved.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(saved.getId())))
                .andExpect(jsonPath("$.beerName", is(saved.getBeerName())));
    }

    @Test
    void testCreateBeer() throws Exception {
        Beer beer = Beer.builder().beerName("New Beer").build();

        mockMvc.perform(post("/api/v1/beer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateBeer() throws Exception {
        Beer saved = beerRepository.save(Beer.builder()
                .beerName("Update Me")
                .build());

        Beer beer = Beer.builder().beerName("Updated Beer").build();

        mockMvc.perform(put("/api/v1/beer/" + saved.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteBeer() throws Exception {
        Beer saved = beerRepository.save(Beer.builder()
                .beerName("Delete Me")
                .build());

        mockMvc.perform(delete("/api/v1/beer/" + saved.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
