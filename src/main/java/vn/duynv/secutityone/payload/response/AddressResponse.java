package vn.duynv.secutityone.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class AddressResponse {
    private Long id;

    private String type;

    private String street;

    private String city;

    private String district;

    private String postalCode;

    private Boolean isDefault;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private int user_id;
}
