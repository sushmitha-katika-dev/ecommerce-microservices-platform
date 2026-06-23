package com.ecommerce.productservice.infrastructure.persistence;

import com.ecommerce.productservice.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
    boolean existsBySku(String sku);
}
