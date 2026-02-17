package walshe.juniemvc.juniemvc.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import walshe.juniemvc.juniemvc.models.BeerDto;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BeerServiceTest {

    @Autowired
    BeerService beerService;

    @Test
    void testListBeers() {
        beerService.saveNewBeer(BeerDto.builder().beerName("Beer 1").build());
        Page<BeerDto> beers = beerService.listBeers(null, 1, 10);
        assertThat(beers.getContent().size()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void testGetBeerById() {
        BeerDto saved = beerService.saveNewBeer(BeerDto.builder().beerName("Beer 2").build());
        Optional<BeerDto> found = beerService.getBeerById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getBeerName()).isEqualTo("Beer 2");
    }

    @Test
    void testSaveNewBeer() {
        BeerDto beer = BeerDto.builder().beerName("Beer 3").build();
        BeerDto saved = beerService.saveNewBeer(beer);
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void testUpdateBeerById() {
        BeerDto saved = beerService.saveNewBeer(BeerDto.builder().beerName("Beer 4").build());
        BeerDto update = BeerDto.builder().beerName("Updated Beer 4").build();
        
        beerService.updateBeerById(saved.getId(), update);
        
        BeerDto found = beerService.getBeerById(saved.getId()).get();
        assertThat(found.getBeerName()).isEqualTo("Updated Beer 4");
    }

    @Test
    void testDeleteById() {
        BeerDto saved = beerService.saveNewBeer(BeerDto.builder().beerName("Beer 5").build());
        beerService.deleteById(saved.getId());
        Optional<BeerDto> found = beerService.getBeerById(saved.getId());
        assertThat(found).isEmpty();
    }
    @Test
    void testListBeersWithName() {
        beerService.saveNewBeer(BeerDto.builder().beerName("Zesty Ale").build());
        Page<BeerDto> beers = beerService.listBeers("Ale", 1, 10);
        assertThat(beers.getContent()).anyMatch(b -> b.getBeerName().toLowerCase().contains("ale"));
    }
}
