package com.ecommerce.cart_service.mapper;

import com.ecommerce.cart_service.dto.response.CartItemResponse;
import com.ecommerce.cart_service.dto.response.CartResponse;
import com.ecommerce.cart_service.entity.Cart;
import com.ecommerce.cart_service.entity.CartItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Component
public class CartMapper {
    public CartResponse toResponse(Cart cart) {
        if (cart == null) {
            return null;
        }
        
        BigDecimal totalAmount = cart.getItems() != null ? 
            cart.getItems().stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add) 
            : BigDecimal.ZERO;

        return CartResponse.builder()
                .id(cart.getId())
                .sessionId(cart.getSessionId())
                .totalAmount(totalAmount)
                .items(cart.getItems() != null ? cart.getItems().stream()
                        .map(this::toItemResponse)
                        .collect(Collectors.toList()) : null)
                .build();
    }

    public CartItemResponse toItemResponse(CartItem item) {
        if (item == null) {
            return null;
        }
        
        BigDecimal subtotal = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));

        return CartItemResponse.builder()
                .productId(item.getProductId())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .subtotal(subtotal)
                .build();
    }
}
