package walshe.juniemvc.juniemvc.models;

import java.math.BigDecimal;

public record PatchBeerOrderCommand(
        String customerRef,
        BigDecimal paymentAmount,
        String status,
        String notes
) {
}
