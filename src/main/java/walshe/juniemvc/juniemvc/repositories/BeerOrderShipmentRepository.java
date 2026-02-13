package walshe.juniemvc.juniemvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import walshe.juniemvc.juniemvc.entities.BeerOrderShipment;

public interface BeerOrderShipmentRepository extends JpaRepository<BeerOrderShipment, Integer> {
}
