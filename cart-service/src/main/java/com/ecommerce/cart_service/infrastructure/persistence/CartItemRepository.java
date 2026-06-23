package com.ecommerce.cart_service.infrastructure.persistence;

import com.ecommerce.cart_service.domain.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, String> {
}
