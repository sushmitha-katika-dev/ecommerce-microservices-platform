package com.ecommerce.payment_service.repository;

import com.ecommerce.payment_service.controller.*;
import com.ecommerce.payment_service.entity.*;
import com.ecommerce.payment_service.repository.*;
import com.ecommerce.payment_service.service.*;
import com.ecommerce.payment_service.dto.response.*;
import com.ecommerce.payment_service.kafka.consumer.*;
import com.ecommerce.payment_service.kafka.event.*;
import com.ecommerce.payment_service.kafka.producer.*;
import com.ecommerce.payment_service.service.impl.*;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    List<Payment> findByOrderId(String orderId);
    List<Payment> findByUserId(String userId);
}

