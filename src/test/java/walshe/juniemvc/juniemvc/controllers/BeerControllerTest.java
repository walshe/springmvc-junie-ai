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
import walshe.juniemvc.juniemvc.models.BeerDto;
import walshe.juniemvc.juniemvc.repositories.BeerRepository;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.containsString;
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
                .param("page", "1")
                .param("size", "10")
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
        BeerDto beerDto = BeerDto.builder()
                .beerName("New Beer")
                .beerStyle("PALE_ALE")
                .upc("123")
                .price(new BigDecimal("1.99"))
                .quantityOnHand(10)
                .build();

        mockMvc.perform(post("/api/v1/beer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beerDto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/api/v1/beer/")));
    }

    @Test
    void testUpdateBeer() throws Exception {
        Beer saved = beerRepository.save(Beer.builder()
                .beerName("Update Me")
                .build());

        BeerDto beerDto = BeerDto.builder()
                .beerName("Updated Beer")
                .beerStyle("PALE_ALE")
                .upc("999")
                .price(new BigDecimal("2.49"))
                .quantityOnHand(5)
                .build();

        mockMvc.perform(put("/api/v1/beer/" + saved.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beerDto)))
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
    @Test
    void testListBeersWithNameFilter() throws Exception {
        beerRepository.save(Beer.builder().beerName("Lager One").beerStyle("LAGER").upc("001").build());
        beerRepository.save(Beer.builder().beerName("Ale Two").beerStyle("ALE").upc("002").build());

        mockMvc.perform(get("/api/v1/beer")
                        .param("beerName", "Ale")
                        .param("page", "1")
                        .param("size", "5")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void testListBeersWithStyleFilter() throws Exception {
        beerRepository.save(Beer.builder().beerName("Style Beer").beerStyle("STOUT").upc("003").build());

        mockMvc.perform(get("/api/v1/beer")
                        .param("beerStyle", "stout")
                        .param("page", "1")
                        .param("size", "5")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].beerStyle", is("STOUT")));
    }
}
