package com.ecommerce.payment_service.dto.response;

import com.ecommerce.payment_service.controller.*;
import com.ecommerce.payment_service.entity.*;
import com.ecommerce.payment_service.repository.*;
import com.ecommerce.payment_service.service.*;
import com.ecommerce.payment_service.dto.response.*;
import com.ecommerce.payment_service.kafka.consumer.*;
import com.ecommerce.payment_service.kafka.event.*;
import com.ecommerce.payment_service.kafka.producer.*;
import com.ecommerce.payment_service.service.impl.*;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private String id;
    private String orderId;
    private String userId;
    private BigDecimal amount;
    private PaymentStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

