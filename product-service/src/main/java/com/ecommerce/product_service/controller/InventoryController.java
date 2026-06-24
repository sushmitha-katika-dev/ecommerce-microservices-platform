package com.ecommerce.product_service.controller;

import com.ecommerce.product_service.controller.*;
import com.ecommerce.product_service.entity.*;
import com.ecommerce.product_service.exception.*;
import com.ecommerce.product_service.repository.*;
import com.ecommerce.product_service.dto.request.*;
import com.ecommerce.product_service.dto.response.*;
import com.ecommerce.product_service.kafka.consumer.*;
import com.ecommerce.product_service.kafka.event.*;
import com.ecommerce.product_service.kafka.producer.*;
import com.ecommerce.product_service.service.impl.*;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{productId}/available")
    public ResponseEntity<Integer> getAvailableQuantity(@PathVariable String productId) {
        return ResponseEntity.ok(inventoryService.getAvailableQuantity(productId));
    }

    @PutMapping
    public ResponseEntity<Void> updateInventory(@Valid @RequestBody InventoryUpdateRequest request) {
        inventoryService.updateInventory(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{productId}/reserve")
    public ResponseEntity<Void> reserveInventory(
            @PathVariable String productId,
            @RequestParam int quantity) {
        inventoryService.reserveInventory(productId, quantity);
        return ResponseEntity.ok().build();
    }
}

