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
    private Integer id;
    private Integer version;
    private String customerRef;
    private BigDecimal paymentAmount;
    private String status;
    private List<BeerOrderLineDto> beerOrderLines;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
