package walshe.juniemvc.juniemvc.models;

import java.time.LocalDateTime;

public record CreateBeerOrderShipmentCommand(
        Integer beerOrderId,
        LocalDateTime shipmentDate,
        String carrier,
        String trackingNumber
) {
}
