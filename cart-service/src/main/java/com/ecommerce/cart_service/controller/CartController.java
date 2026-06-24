package com.ecommerce.cart_service.controller;

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


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/{sessionId}")
    public ResponseEntity<CartResponse> getCart(@PathVariable String sessionId) {
        return ResponseEntity.ok(cartService.getCart(sessionId));
    }

    @PostMapping("/{sessionId}/items")
    public ResponseEntity<CartResponse> addItem(
            @PathVariable String sessionId,
            @Valid @RequestBody CartItemRequest request) {
        return ResponseEntity.ok(cartService.addItem(sessionId, request));
    }

    @DeleteMapping("/{sessionId}/items/{productId}")
    public ResponseEntity<CartResponse> removeItem(
            @PathVariable String sessionId,
            @PathVariable String productId) {
        return ResponseEntity.ok(cartService.removeItem(sessionId, productId));
    }

    @PostMapping("/{sessionId}/checkout")
    public ResponseEntity<Void> checkoutCart(@PathVariable String sessionId) {
        cartService.checkoutCart(sessionId);
        return ResponseEntity.ok().build();
    }
}

