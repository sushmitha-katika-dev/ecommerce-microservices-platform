package com.ecommerce.cart_service.presentation.controllers;

import com.ecommerce.cart_service.application.dtos.CartItemRequest;
import com.ecommerce.cart_service.application.dtos.CartResponse;
import com.ecommerce.cart_service.application.services.CartService;
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
}
