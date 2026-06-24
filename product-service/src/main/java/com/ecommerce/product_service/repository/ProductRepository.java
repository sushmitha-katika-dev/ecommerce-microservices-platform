package com.ecommerce.product_service.repository;

import com.ecommerce.product_service.entity.*;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
    boolean existsBySku(String sku);
}
