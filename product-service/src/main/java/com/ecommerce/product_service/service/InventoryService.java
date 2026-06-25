package com.ecommerce.product_service.service;

import com.ecommerce.product_service.dto.request.InventoryUpdateRequest;

public interface InventoryService {
    Integer getAvailableQuantity(String productId);

    void updateInventory(InventoryUpdateRequest request);

    void reserveInventory(String productId, int quantity);

    void decrementInventory(String productId, int quantity);
}
