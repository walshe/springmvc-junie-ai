package walshe.juniemvc.juniemvc.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import walshe.juniemvc.juniemvc.entities.Beer;

public interface BeerRepository extends JpaRepository<Beer, Integer> {
    Page<Beer> findAllByBeerNameIsLikeIgnoreCase(String beerName, Pageable pageable);
}
