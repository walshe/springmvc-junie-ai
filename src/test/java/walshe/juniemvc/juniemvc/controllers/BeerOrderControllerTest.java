package walshe.juniemvc.juniemvc.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import walshe.juniemvc.juniemvc.entities.Beer;
import walshe.juniemvc.juniemvc.models.BeerOrderDto;
import walshe.juniemvc.juniemvc.models.CreateBeerOrderCommand;
import walshe.juniemvc.juniemvc.models.PatchBeerOrderCommand;
import walshe.juniemvc.juniemvc.repositories.BeerOrderRepository;
import walshe.juniemvc.juniemvc.repositories.BeerRepository;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BeerOrderControllerTest {

    @LocalServerPort
    int port;


    @Autowired
    BeerOrderRepository beerOrderRepository;

    @Autowired
    BeerRepository beerRepository;

    Integer beerId;

    @BeforeEach
    void setUp() {
        beerOrderRepository.deleteAll();
        beerRepository.deleteAll();
        Beer saved = beerRepository.save(Beer.builder()
                .beerName("Test IPA")
                .beerStyle("IPA")
                .upc("12345")
                .quantityOnHand(100)
                .price(new BigDecimal("4.50"))
                .build());
        beerId = saved.getId();
    }

    @Test
    void createAndFetchOrder() {
        String basePath = "/api/v1/beerOrder";

        CreateBeerOrderCommand.CreateBeerOrderLineCommand line =
                new CreateBeerOrderCommand.CreateBeerOrderLineCommand(beerId, 2);
        CreateBeerOrderCommand cmd = new CreateBeerOrderCommand("REF-001", "Order notes", List.of(line));

        RestClient client = RestClient.builder()
                .baseUrl("http://localhost:" + port)
                .build();

        var createResp = client.post()
                .uri(basePath)
                .contentType(MediaType.APPLICATION_JSON)
                .body(cmd)
                .retrieve()
                .toEntity(BeerOrderDto.class);

        assertThat(createResp.getStatusCode().is2xxSuccessful()).isTrue();
        var created = createResp.getBody();
        assertThat(created).isNotNull();
        assertThat(created.getId()).isNotNull();
        assertThat(created.getNotes()).isEqualTo("Order notes");
        assertThat(created.getBeerOrderLines()).hasSize(1);

        // GET created
        var getResp = client.get()
                .uri(basePath + "/" + created.getId())
                .retrieve()
                .toEntity(BeerOrderDto.class);
        assertThat(getResp.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(getResp.getBody()).isNotNull();
        assertThat(getResp.getBody().getCustomerRef()).isEqualTo("REF-001");

        // LIST
        var listResp = client.get()
                .uri(basePath)
                .retrieve()
                .toEntity(new org.springframework.core.ParameterizedTypeReference<java.util.List<BeerOrderDto>>(){});
        assertThat(listResp.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(listResp.getBody()).isNotNull();
        assertThat(listResp.getBody().size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    void patchOrder() {
        String basePath = "/api/v1/beerOrder";

        CreateBeerOrderCommand.CreateBeerOrderLineCommand line =
                new CreateBeerOrderCommand.CreateBeerOrderLineCommand(beerId, 2);
        CreateBeerOrderCommand createCmd = new CreateBeerOrderCommand("REF-PATCH", "Original notes", List.of(line));

        RestClient client = RestClient.builder()
                .baseUrl("http://localhost:" + port)
                .build();

        var createResp = client.post()
                .uri(basePath)
                .contentType(MediaType.APPLICATION_JSON)
                .body(createCmd)
                .retrieve()
                .toEntity(BeerOrderDto.class);

        var created = createResp.getBody();
        assertThat(created).isNotNull();

        // PATCH only the notes
        PatchBeerOrderCommand patchCmd = new PatchBeerOrderCommand(null, null, null, "Updated notes");

        var patchResp = client.patch()
                .uri(basePath + "/" + created.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .body(patchCmd)
                .retrieve()
                .toBodilessEntity();

        assertThat(patchResp.getStatusCode().is2xxSuccessful()).isTrue();

        // Verify changes
        var getResp = client.get()
                .uri(basePath + "/" + created.getId())
                .retrieve()
                .toEntity(BeerOrderDto.class);

        var updated = getResp.getBody();
        assertThat(updated).isNotNull();
        assertThat(updated.getNotes()).isEqualTo("Updated notes");
        assertThat(updated.getCustomerRef()).isEqualTo("REF-PATCH"); // Should remain unchanged
    }
}
