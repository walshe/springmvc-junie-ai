package walshe.juniemvc.juniemvc.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import walshe.juniemvc.juniemvc.entities.BeerOrder;

import java.util.Optional;

public interface BeerOrderRepository extends JpaRepository<BeerOrder, Integer> {

    @EntityGraph(attributePaths = {"beerOrderLines", "beerOrderLines.beer"})
    Optional<BeerOrder> findWithLinesById(Integer id);
}
