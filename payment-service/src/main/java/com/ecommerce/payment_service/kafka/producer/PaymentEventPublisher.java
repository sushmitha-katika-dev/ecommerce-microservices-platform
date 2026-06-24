package com.ecommerce.payment_service.kafka.producer;

import com.ecommerce.payment_service.controller.*;
import com.ecommerce.payment_service.entity.*;
import com.ecommerce.payment_service.repository.*;
import com.ecommerce.payment_service.service.*;
import com.ecommerce.payment_service.dto.response.*;
import com.ecommerce.payment_service.kafka.consumer.*;
import com.ecommerce.payment_service.kafka.event.*;
import com.ecommerce.payment_service.kafka.producer.*;
import com.ecommerce.payment_service.service.impl.*;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String COMPLETED_TOPIC = "payment-completed";
    private static final String FAILED_TOPIC = "payment-failed";

    public void publishPaymentCompletedEvent(PaymentCompletedEvent event) {
        log.info("Publishing payment completed event for orderId: {}", event.getOrderId());
        kafkaTemplate.send(COMPLETED_TOPIC, event.getOrderId(), event);
    }

    public void publishPaymentFailedEvent(PaymentFailedEvent event) {
        log.info("Publishing payment failed event for orderId: {}", event.getOrderId());
        kafkaTemplate.send(FAILED_TOPIC, event.getOrderId(), event);
    }
}

