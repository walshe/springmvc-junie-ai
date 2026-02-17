package walshe.juniemvc.juniemvc.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerOrderDto {

    // readonly
    private Integer id;
    private Integer version;

    private String customerRef;

    // amount in USD and cents
    private BigDecimal paymentAmount;

    // status of the order as a whole PENDING, DELIVERED
    private String status;

    private String notes;

    private List<BeerOrderLineDto> beerOrderLines;

    //read only
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
