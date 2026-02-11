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
    BeerRepository beerRepository;

    Integer beerId;

    @BeforeEach
    void setUp() {
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
        CreateBeerOrderCommand cmd = new CreateBeerOrderCommand("REF-001", List.of(line));

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
}
