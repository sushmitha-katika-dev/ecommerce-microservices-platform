package com.ecommerce.cart_service.application.services;

import com.ecommerce.cart_service.application.dtos.CartItemRequest;
import com.ecommerce.cart_service.application.dtos.CartResponse;

public interface CartService {
    CartResponse getCart(String sessionId);
    CartResponse addItem(String sessionId, CartItemRequest request);
    CartResponse removeItem(String sessionId, String productId);
}
