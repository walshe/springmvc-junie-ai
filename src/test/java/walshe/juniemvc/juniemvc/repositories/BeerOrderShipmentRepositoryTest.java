package walshe.juniemvc.juniemvc.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import walshe.juniemvc.juniemvc.entities.BeerOrder;
import walshe.juniemvc.juniemvc.entities.BeerOrderShipment;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BeerOrderShipmentRepositoryTest {

    @Autowired
    BeerOrderShipmentRepository beerOrderShipmentRepository;

    @Autowired
    BeerOrderRepository beerOrderRepository;

    @Test
    @Transactional
    void testSaveShipment() {
        BeerOrder beerOrder = beerOrderRepository.save(BeerOrder.builder().customerRef("TestRef").build());

        BeerOrderShipment shipment = BeerOrderShipment.builder()
                .carrier("UPS")
                .trackingNumber("TRACK-123")
                .shipmentDate(LocalDateTime.now())
                .beerOrder(beerOrder)
                .build();

        BeerOrderShipment saved = beerOrderShipmentRepository.save(shipment);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCarrier()).isEqualTo("UPS");
        assertThat(saved.getBeerOrder().getId()).isEqualTo(beerOrder.getId());
    }
}
