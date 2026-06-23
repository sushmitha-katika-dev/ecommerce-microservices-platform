package com.ecommerce.productservice.infrastructure.persistence;

import com.ecommerce.productservice.domain.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, String> {
    Optional<Inventory> findByProductId(String productId);
}
