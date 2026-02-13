package walshe.juniemvc.juniemvc.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends BaseEntity {

    @NotNull
    private String name;

    private String email;

    @jakarta.persistence.Column(name = "phone_number")
    private String phoneNumber;

    @NotNull
    @jakarta.persistence.Column(name = "address_line_1")
    private String addressLine1;

    @jakarta.persistence.Column(name = "address_line_2")
    private String addressLine2;

    @NotNull
    private String city;

    @NotNull
    private String state;

    @NotNull
    @jakarta.persistence.Column(name = "postal_code")
    private String postalCode;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<BeerOrder> beerOrders = new HashSet<>();

    public void addBeerOrder(BeerOrder beerOrder) {
        if (beerOrders == null) {
            beerOrders = new HashSet<>();
        }
        beerOrders.add(beerOrder);
        beerOrder.setCustomer(this);
    }

    public void removeBeerOrder(BeerOrder beerOrder) {
        beerOrders.remove(beerOrder);
        beerOrder.setCustomer(null);
    }
}
