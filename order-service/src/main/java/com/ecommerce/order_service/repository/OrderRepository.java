package com.ecommerce.order_service.repository;

import com.ecommerce.order_service.controller.*;
import com.ecommerce.order_service.entity.*;
import com.ecommerce.order_service.exception.*;
import com.ecommerce.order_service.repository.*;
import com.ecommerce.order_service.service.*;
import com.ecommerce.order_service.dto.request.*;
import com.ecommerce.order_service.dto.response.*;
import com.ecommerce.order_service.kafka.event.*;
import com.ecommerce.order_service.kafka.producer.*;
import com.ecommerce.order_service.service.impl.*;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByUserIdOrderByCreatedAtDesc(String userId);
}

