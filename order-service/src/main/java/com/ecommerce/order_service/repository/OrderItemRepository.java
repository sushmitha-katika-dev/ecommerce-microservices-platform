package com.ecommerce.order_service.repository;

import com.ecommerce.order_service.entity.*;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, String> {
}
