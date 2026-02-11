package walshe.juniemvc.juniemvc.models;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerDto {

    private Integer id;
    private Integer version;

    @NotBlank
    private String beerName;

    @NotBlank
    private String beerStyle;

    @NotBlank
    private String upc;

    @NotNull
    @Min(0)
    private Integer quantityOnHand;

    @NotNull
    @DecimalMin("0.00")
    private BigDecimal price;

    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
