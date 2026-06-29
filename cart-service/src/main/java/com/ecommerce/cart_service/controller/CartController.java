package com.ecommerce.cart_service.controller;

import com.ecommerce.cart_service.service.*;
import com.ecommerce.cart_service.dto.request.*;
import com.ecommerce.cart_service.dto.response.*;

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

    @PutMapping("/{sessionId}/items/{productId}")
    public ResponseEntity<CartResponse> updateItemQuantity(
            @PathVariable String sessionId,
            @PathVariable String productId,
            @Valid @RequestBody UpdateCartItemRequest request) {
        return ResponseEntity.ok(cartService.updateItemQuantity(sessionId, productId, request));
    }

    @DeleteMapping("/{sessionId}")
    public ResponseEntity<Void> clearCart(@PathVariable String sessionId) {
        cartService.clearCart(sessionId);
        return ResponseEntity.noContent().build();
    }
}
