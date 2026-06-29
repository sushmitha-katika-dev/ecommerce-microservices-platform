package com.ecommerce.user_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse {
    private String id;
    private String type;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;
}
