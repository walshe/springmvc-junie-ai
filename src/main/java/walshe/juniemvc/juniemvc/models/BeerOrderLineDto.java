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
    private Integer id;
    private Integer version;
    private Integer beerId;
    private Integer orderQuantity;
    private Integer quantityAllocated;
    private String status;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
