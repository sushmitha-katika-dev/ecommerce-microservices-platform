package com.ecommerce.cart_service.service;

import com.ecommerce.cart_service.controller.*;
import com.ecommerce.cart_service.entity.*;
import com.ecommerce.cart_service.exception.*;
import com.ecommerce.cart_service.repository.*;
import com.ecommerce.cart_service.service.*;
import com.ecommerce.cart_service.dto.request.*;
import com.ecommerce.cart_service.dto.response.*;
import com.ecommerce.cart_service.kafka.event.*;
import com.ecommerce.cart_service.kafka.producer.*;
import com.ecommerce.cart_service.service.impl.*;



public interface CartService {
    CartResponse getCart(String sessionId);
    CartResponse addItem(String sessionId, CartItemRequest request);
    CartResponse removeItem(String sessionId, String productId);
    void checkoutCart(String sessionId);
}

