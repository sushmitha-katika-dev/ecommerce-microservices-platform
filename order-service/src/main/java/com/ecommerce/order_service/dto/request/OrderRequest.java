package com.ecommerce.order_service.dto.request;

import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;

import lombok.Builder;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    @NotBlank
    private String userId;

    @NotBlank
    private String shippingAddress;

    @NotEmpty
    @Valid
    private List<OrderItemRequest> items;
}
