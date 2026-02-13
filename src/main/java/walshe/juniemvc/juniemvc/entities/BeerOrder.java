package walshe.juniemvc.juniemvc.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BeerOrder extends BaseEntity {

    private String customerRef;
    private BigDecimal paymentAmount;
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "beerOrder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<BeerOrderLine> beerOrderLines = new HashSet<>();

    @OneToMany(mappedBy = "beerOrder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<BeerOrderShipment> beerOrderShipments = new HashSet<>();

    public void addLine(BeerOrderLine line) {
        if (beerOrderLines == null) {
            beerOrderLines = new HashSet<>();
        }
        beerOrderLines.add(line);
        line.setBeerOrder(this);
    }

    public void removeLine(BeerOrderLine line) {
        beerOrderLines.remove(line);
        line.setBeerOrder(null);
    }

    public void addShipment(BeerOrderShipment shipment) {
        if (beerOrderShipments == null) {
            beerOrderShipments = new HashSet<>();
        }
        beerOrderShipments.add(shipment);
        shipment.setBeerOrder(this);
    }

    public void removeShipment(BeerOrderShipment shipment) {
        beerOrderShipments.remove(shipment);
        shipment.setBeerOrder(null);
    }
}
