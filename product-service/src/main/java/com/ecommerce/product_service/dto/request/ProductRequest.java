package com.ecommerce.product_service.dto.request;

import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;

import lombok.Builder;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Builder.Default
    private Boolean active = true;
}
