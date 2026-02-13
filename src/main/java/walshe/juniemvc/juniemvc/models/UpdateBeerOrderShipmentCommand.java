package walshe.juniemvc.juniemvc.models;

import java.time.LocalDateTime;

public record UpdateBeerOrderShipmentCommand(
        LocalDateTime shipmentDate,
        String carrier,
        String trackingNumber
) {
}
