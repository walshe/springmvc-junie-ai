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
public class BeerOrderLineDto {

    // read only
    private Integer id;
    private Integer version;


    private Integer beerId;


    private Integer orderQuantity;
    private Integer quantityAllocated;

    // status of this line item PENDING, DELIVERED
    private String status;

    // read only
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
