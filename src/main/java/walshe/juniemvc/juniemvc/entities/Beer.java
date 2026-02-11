package walshe.juniemvc.juniemvc.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Beer extends BaseEntity {

    @Builder
    public Beer(Integer id, Integer version, java.time.LocalDateTime createdDate, java.time.LocalDateTime updateDate,
                String beerName, String beerStyle, String upc, Integer quantityOnHand, BigDecimal price,
                Set<BeerOrderLine> beerOrderLines) {
        super.setId(id);
        super.setVersion(version);
        super.setCreatedDate(createdDate);
        super.setUpdateDate(updateDate);
        this.beerName = beerName;
        this.beerStyle = beerStyle;
        this.upc = upc;
        this.quantityOnHand = quantityOnHand;
        this.price = price;
        this.beerOrderLines = beerOrderLines != null ? beerOrderLines : new HashSet<>();
    }

    private String beerName;
    private String beerStyle;
    private String upc;
    private Integer quantityOnHand;
    private BigDecimal price;

    @OneToMany(mappedBy = "beer")
    private Set<BeerOrderLine> beerOrderLines = new HashSet<>();
}
