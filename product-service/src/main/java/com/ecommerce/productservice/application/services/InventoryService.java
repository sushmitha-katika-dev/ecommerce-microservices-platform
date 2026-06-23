package com.ecommerce.productservice.application.services;

import com.ecommerce.productservice.application.dtos.InventoryUpdateRequest;
import com.ecommerce.productservice.domain.entities.Inventory;
import com.ecommerce.productservice.domain.exceptions.ResourceNotFoundException;
import com.ecommerce.productservice.infrastructure.persistence.InventoryRepository;
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
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found for product: " + request.getProductId()));
        
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
}
