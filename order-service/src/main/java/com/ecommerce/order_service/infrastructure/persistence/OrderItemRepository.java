package com.ecommerce.order_service.infrastructure.persistence;

import com.ecommerce.order_service.domain.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, String> {
}
