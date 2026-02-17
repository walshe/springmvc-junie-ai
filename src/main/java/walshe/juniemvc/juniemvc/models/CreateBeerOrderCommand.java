package walshe.juniemvc.juniemvc.models;

import java.util.List;

public record CreateBeerOrderCommand(
        String customerRef,
        String notes,
        List<CreateBeerOrderLineCommand> beerOrderLines
) {
    public record CreateBeerOrderLineCommand(
            Integer beerId,
            Integer orderQuantity
    ) {}
}
