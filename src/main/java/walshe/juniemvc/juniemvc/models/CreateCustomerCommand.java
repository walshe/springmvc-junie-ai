package walshe.juniemvc.juniemvc.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCustomerCommand(
        @NotBlank @Size(max = 255) String name,
        @Size(max = 255) String email,
        @JsonProperty("phone_number") @Size(max = 255) String phoneNumber,
        @JsonProperty("address_line_1") @NotBlank @Size(max = 255) String addressLine1,
        @JsonProperty("address_line_2") @Size(max = 255) String addressLine2,
        @NotBlank @Size(max = 255) String city,
        @NotBlank @Size(max = 255) String state,
        @JsonProperty("postal_code") @NotBlank @Size(max = 255) String postalCode
) {
}
