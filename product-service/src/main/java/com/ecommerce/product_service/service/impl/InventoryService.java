package com.ecommerce.product_service.service.impl;

import com.ecommerce.product_service.entity.*;
import com.ecommerce.product_service.exception.*;
import com.ecommerce.product_service.repository.*;
import com.ecommerce.product_service.dto.request.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public Integer getAvailableQuantity(String productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found for product: " + productId));
        return inventory.getAvailableQuantity();
    }

    @Transactional
    public void updateInventory(InventoryUpdateRequest request) {
        Inventory inventory = inventoryRepository.findByProductId(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Inventory not found for product: " + request.getProductId()));

        inventory.setQuantity(inventory.getQuantity() + request.getQuantity());
        inventoryRepository.save(inventory);
    }

    @Transactional
    public void reserveInventory(String productId, int quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found for product: " + productId));

        if (inventory.getAvailableQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient inventory");
        }

        inventory.setReservedQuantity(inventory.getReservedQuantity() + quantity);
        inventoryRepository.save(inventory);
    }

    @Transactional
    public void decrementInventory(String productId, int quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found for product: " + productId));

        // Decrement actual quantity. If it was reserved, we might also want to
        // decrement reserved, but for simplicity we just reduce overall quantity.
        inventory.setQuantity(inventory.getQuantity() - quantity);
        inventoryRepository.save(inventory);
    }
}
