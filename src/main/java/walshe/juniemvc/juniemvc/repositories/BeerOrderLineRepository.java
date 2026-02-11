package walshe.juniemvc.juniemvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import walshe.juniemvc.juniemvc.entities.BeerOrderLine;

public interface BeerOrderLineRepository extends JpaRepository<BeerOrderLine, Integer> {
}
