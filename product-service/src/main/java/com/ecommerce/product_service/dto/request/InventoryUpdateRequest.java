package com.ecommerce.product_service.dto.request;

import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;

import lombok.Builder;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryUpdateRequest {
    @NotBlank
    private String productId;

    @NotNull
    @Min(0)
    private Integer quantity;
}
