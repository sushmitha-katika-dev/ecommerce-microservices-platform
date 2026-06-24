package com.ecommerce.notification_service.kafka.event;

import com.ecommerce.notification_service.controller.*;
import com.ecommerce.notification_service.entity.*;
import com.ecommerce.notification_service.repository.*;
import com.ecommerce.notification_service.kafka.consumer.*;
import com.ecommerce.notification_service.kafka.event.*;
import com.ecommerce.notification_service.service.impl.*;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderCreatedEvent {
    private String orderId;
    private String userId;
    private BigDecimal amount;
}

