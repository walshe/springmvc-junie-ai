package walshe.juniemvc.juniemvc.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    private Integer id;
    private Integer version;

    @NotBlank
    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String email;

    @JsonProperty("phone_number")
    @Size(max = 255)
    private String phoneNumber;

    @JsonProperty("address_line_1")
    @NotBlank
    @Size(max = 255)
    private String addressLine1;

    @JsonProperty("address_line_2")
    @Size(max = 255)
    private String addressLine2;

    @NotBlank
    @Size(max = 255)
    private String city;

    @NotBlank
    @Size(max = 255)
    private String state;

    @JsonProperty("postal_code")
    @NotBlank
    @Size(max = 255)
    private String postalCode;

    @JsonProperty("created_date")
    private LocalDateTime createdDate;

    @JsonProperty("update_date")
    private LocalDateTime updateDate;
}
