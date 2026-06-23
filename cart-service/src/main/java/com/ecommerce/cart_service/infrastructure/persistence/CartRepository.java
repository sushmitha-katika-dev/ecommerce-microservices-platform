package com.ecommerce.cart_service.infrastructure.persistence;

import com.ecommerce.cart_service.domain.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, String> {
    Optional<Cart> findBySessionId(String sessionId);
}
