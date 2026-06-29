package com.ecommerce.user_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {
    
    @NotBlank(message = "Address type is required")
    private String type;
    
    @NotBlank(message = "Street is required")
    private String street;
    
    @NotBlank(message = "City is required")
    private String city;
    
    private String state;
    
    private String zipCode;
    
    @NotBlank(message = "Country is required")
    private String country;
}
