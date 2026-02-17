package walshe.juniemvc.juniemvc.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import walshe.juniemvc.juniemvc.entities.BeerOrder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BeerOrderRepositoryTest {

    @Autowired
    BeerOrderRepository beerOrderRepository;

    @Test
    void testSaveBeerOrder() {
        BeerOrder savedBeerOrder = beerOrderRepository.save(BeerOrder.builder()
                .customerRef("Test Order")
                .notes("Test Notes")
                .build());

        assertThat(savedBeerOrder).isNotNull();
        assertThat(savedBeerOrder.getId()).isNotNull();
        assertThat(savedBeerOrder.getNotes()).isEqualTo("Test Notes");
    }

    @Test
    void testGetBeerOrder() {
        BeerOrder savedBeerOrder = beerOrderRepository.save(BeerOrder.builder()
                .customerRef("Test Order")
                .notes("More Test Notes")
                .build());

        BeerOrder fetchedBeerOrder = beerOrderRepository.findById(savedBeerOrder.getId()).get();

        assertThat(fetchedBeerOrder).isNotNull();
        assertThat(fetchedBeerOrder.getNotes()).isEqualTo("More Test Notes");
    }
}
