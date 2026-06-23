package com.ecommerce.productservice.application.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {
    @NotBlank
    private String categoryId;
    
    @NotBlank
    private String name;
    
    @NotBlank
    private String sku;
    
    private String description;
    
    @NotNull
    @DecimalMin("0.0")
    private BigDecimal price;
    
    private boolean active = true;
}
