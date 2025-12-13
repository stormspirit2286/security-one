package vn.duynv.secutityone.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.duynv.secutityone.modal.TypeAddress;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreationAddressDto {
    @NotNull(message = "Type is required")
    private TypeAddress type;

    @NotBlank(message = "Street is required")
    private String street;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "District is required")
    private String district;

    private String postalCode;

    @Builder.Default
    private Boolean isDefault = false;
}
