package walshe.juniemvc.juniemvc.mappers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import walshe.juniemvc.juniemvc.entities.BeerOrder;
import walshe.juniemvc.juniemvc.entities.BeerOrderShipment;
import walshe.juniemvc.juniemvc.models.BeerOrderShipmentDto;
import walshe.juniemvc.juniemvc.models.CreateBeerOrderShipmentCommand;
import walshe.juniemvc.juniemvc.models.UpdateBeerOrderShipmentCommand;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BeerOrderShipmentMapperTest {

    @Autowired
    BeerOrderShipmentMapper mapper;

    @Test
    void beerOrderShipmentToBeerOrderShipmentDto() {
        BeerOrder beerOrder = BeerOrder.builder().build();
        beerOrder.setId(1);

        BeerOrderShipment entity = BeerOrderShipment.builder()
                .shipmentDate(LocalDateTime.now())
                .carrier("FedEx")
                .trackingNumber("12345")
                .beerOrder(beerOrder)
                .build();
        entity.setId(100);

        BeerOrderShipmentDto dto = mapper.beerOrderShipmentToBeerOrderShipmentDto(entity);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(100);
        assertThat(dto.getBeerOrderId()).isEqualTo(1);
        assertThat(dto.getCarrier()).isEqualTo("FedEx");
    }

    @Test
    void createCommandToBeerOrderShipment() {
        CreateBeerOrderShipmentCommand command = new CreateBeerOrderShipmentCommand(1, LocalDateTime.now(), "UPS", "TRACK-1");

        BeerOrderShipment entity = mapper.createCommandToBeerOrderShipment(command);

        assertThat(entity).isNotNull();
        assertThat(entity.getCarrier()).isEqualTo("UPS");
        assertThat(entity.getTrackingNumber()).isEqualTo("TRACK-1");
    }

    @Test
    void updateBeerOrderShipmentFromCommand() {
        BeerOrderShipment entity = BeerOrderShipment.builder()
                .carrier("Old Carrier")
                .build();

        UpdateBeerOrderShipmentCommand command = new UpdateBeerOrderShipmentCommand(LocalDateTime.now(), "New Carrier", "NEW-TRACK");

        mapper.updateBeerOrderShipmentFromCommand(command, entity);

        assertThat(entity.getCarrier()).isEqualTo("New Carrier");
        assertThat(entity.getTrackingNumber()).isEqualTo("NEW-TRACK");
    }
}
