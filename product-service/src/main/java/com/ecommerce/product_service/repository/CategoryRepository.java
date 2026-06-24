package com.ecommerce.product_service.repository;

import com.ecommerce.product_service.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {
    Optional<Category> findBySlug(String slug);

    boolean existsByNameOrSlug(String name, String slug);
}
