package walshe.juniemvc.juniemvc.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import walshe.juniemvc.juniemvc.entities.Beer;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveBeer() {
        Beer savedBeer = beerRepository.save(Beer.builder()
                .beerName("My Beer")
                .beerStyle("PALE_ALE")
                .upc("234234234234")
                .build());

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }

    @Test
    void testGetBeer() {
        Beer savedBeer = beerRepository.save(Beer.builder()
                .beerName("My Beer")
                .beerStyle("PALE_ALE")
                .upc("234234234234")
                .build());

        Beer fetchedBeer = beerRepository.findById(savedBeer.getId()).get();

        assertThat(fetchedBeer).isNotNull();
        assertThat(fetchedBeer.getBeerName()).isEqualTo("My Beer");
    }

    @Test
    void testUpdateBeer() {
        Beer savedBeer = beerRepository.save(Beer.builder()
                .beerName("My Beer")
                .beerStyle("PALE_ALE")
                .upc("234234234234")
                .build());

        Beer fetchedBeer = beerRepository.findById(savedBeer.getId()).get();
        fetchedBeer.setBeerName("Updated Beer Name");
        Beer updatedBeer = beerRepository.save(fetchedBeer);

        assertThat(updatedBeer.getBeerName()).isEqualTo("Updated Beer Name");
    }

    @Test
    void testDeleteBeer() {
        Beer savedBeer = beerRepository.save(Beer.builder()
                .beerName("My Beer")
                .beerStyle("PALE_ALE")
                .upc("234234234234")
                .build());

        beerRepository.deleteById(savedBeer.getId());

        assertThat(beerRepository.findById(savedBeer.getId())).isEmpty();
    }
    @Test
    void testFindAllByBeerNameLikeIgnoreCase() {
        beerRepository.save(Beer.builder().beerName("Crisp Lager").beerStyle("LAGER").upc("111").build());
        beerRepository.save(Beer.builder().beerName("Amber Ale").beerStyle("ALE").upc("222").build());
        Pageable pageable = PageRequest.of(0, 10);
        Page<Beer> page = beerRepository.findAllByBeerNameIsLikeIgnoreCase("%ale%", pageable);
        assertThat(page.getContent()).anyMatch(b -> b.getBeerName().toLowerCase().contains("ale"));
    }
}
