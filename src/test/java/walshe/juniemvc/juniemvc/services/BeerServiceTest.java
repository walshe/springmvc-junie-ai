package walshe.juniemvc.juniemvc.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import walshe.juniemvc.juniemvc.entities.Beer;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BeerServiceTest {

    @Autowired
    BeerService beerService;

    @Test
    void testListBeers() {
        beerService.saveNewBeer(Beer.builder().beerName("Beer 1").build());
        List<Beer> beers = beerService.listBeers();
        assertThat(beers.size()).isGreaterThan(0);
    }

    @Test
    void testGetBeerById() {
        Beer saved = beerService.saveNewBeer(Beer.builder().beerName("Beer 2").build());
        Optional<Beer> found = beerService.getBeerById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getBeerName()).isEqualTo("Beer 2");
    }

    @Test
    void testSaveNewBeer() {
        Beer beer = Beer.builder().beerName("Beer 3").build();
        Beer saved = beerService.saveNewBeer(beer);
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void testUpdateBeerById() {
        Beer saved = beerService.saveNewBeer(Beer.builder().beerName("Beer 4").build());
        Beer update = Beer.builder().beerName("Updated Beer 4").build();
        
        beerService.updateBeerById(saved.getId(), update);
        
        Beer found = beerService.getBeerById(saved.getId()).get();
        assertThat(found.getBeerName()).isEqualTo("Updated Beer 4");
    }

    @Test
    void testDeleteById() {
        Beer saved = beerService.saveNewBeer(Beer.builder().beerName("Beer 5").build());
        beerService.deleteById(saved.getId());
        Optional<Beer> found = beerService.getBeerById(saved.getId());
        assertThat(found).isEmpty();
    }
}
