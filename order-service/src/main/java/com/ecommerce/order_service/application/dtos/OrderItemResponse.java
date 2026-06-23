package com.ecommerce.order_service.application.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderItemResponse {
    private String id;
    private String productId;
    private Integer quantity;
    private BigDecimal unitPrice;
}
