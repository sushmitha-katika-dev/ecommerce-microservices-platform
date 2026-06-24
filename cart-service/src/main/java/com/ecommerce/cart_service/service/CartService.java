package com.ecommerce.cart_service.service;

import com.ecommerce.cart_service.dto.request.*;
import com.ecommerce.cart_service.dto.response.*;

public interface CartService {
    CartResponse getCart(String sessionId);

    CartResponse addItem(String sessionId, CartItemRequest request);

    CartResponse removeItem(String sessionId, String productId);

    void checkoutCart(String sessionId);
}
