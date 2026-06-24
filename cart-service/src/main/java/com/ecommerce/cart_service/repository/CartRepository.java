package com.ecommerce.cart_service.repository;

import com.ecommerce.cart_service.controller.*;
import com.ecommerce.cart_service.entity.*;
import com.ecommerce.cart_service.exception.*;
import com.ecommerce.cart_service.repository.*;
import com.ecommerce.cart_service.service.*;
import com.ecommerce.cart_service.dto.request.*;
import com.ecommerce.cart_service.dto.response.*;
import com.ecommerce.cart_service.kafka.event.*;
import com.ecommerce.cart_service.kafka.producer.*;
import com.ecommerce.cart_service.service.impl.*;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, String> {
    Optional<Cart> findBySessionId(String sessionId);
}

