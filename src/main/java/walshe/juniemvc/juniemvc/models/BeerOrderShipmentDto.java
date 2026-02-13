package walshe.juniemvc.juniemvc.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerOrderShipmentDto {

    private Integer id;
    private Integer version;
    private Integer beerOrderId;
    private LocalDateTime shipmentDate;
    private String carrier;
    private String trackingNumber;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
