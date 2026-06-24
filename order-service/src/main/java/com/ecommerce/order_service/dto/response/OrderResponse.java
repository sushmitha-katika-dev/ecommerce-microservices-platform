package com.ecommerce.order_service.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.ecommerce.order_service.enums.OrderStatus;

@Data
@Builder
public class OrderResponse {
    private String id;
    private String userId;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private String shippingAddress;
    private LocalDateTime createdAt;
    private List<OrderItemResponse> items;
}
