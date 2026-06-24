package com.ecommerce.order_service.dto.response;

import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {
    private String id;
    private String productId;
    private Integer quantity;
    private BigDecimal unitPrice;
}
