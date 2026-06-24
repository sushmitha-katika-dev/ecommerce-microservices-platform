package com.ecommerce.cart_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.cart_service.entity.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {
}
