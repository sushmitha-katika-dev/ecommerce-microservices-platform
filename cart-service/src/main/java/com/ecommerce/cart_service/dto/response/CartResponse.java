package com.ecommerce.cart_service.dto.response;

import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
    private String id;
    private String sessionId;
    private List<CartItemResponse> items;
    private BigDecimal totalAmount;
}

