package walshe.juniemvc.juniemvc.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateCustomerCommand(
        @NotBlank @Size(max = 255) String name,
        @Size(max = 255) String email,
        @Size(max = 255) String phoneNumber,
        @NotBlank @Size(max = 255) String addressLine1,
        @Size(max = 255) String addressLine2,
        @NotBlank @Size(max = 255) String city,
        @NotBlank @Size(max = 255) String state,
        @NotBlank @Size(max = 255) String postalCode
) {
}
