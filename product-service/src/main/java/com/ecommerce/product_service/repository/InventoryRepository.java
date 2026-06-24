package com.ecommerce.product_service.repository;

import com.ecommerce.product_service.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, String> {
    Optional<Inventory> findByProductId(String productId);
}
