package com.ecommerce.order_service.infrastructure.persistence;

import com.ecommerce.order_service.domain.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByUserIdOrderByCreatedAtDesc(String userId);
}
