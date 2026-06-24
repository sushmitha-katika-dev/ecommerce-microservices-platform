package com.ecommerce.notification_service.kafka.consumer;

import com.ecommerce.notification_service.controller.*;
import com.ecommerce.notification_service.entity.*;
import com.ecommerce.notification_service.repository.*;
import com.ecommerce.notification_service.kafka.consumer.*;
import com.ecommerce.notification_service.kafka.event.*;
import com.ecommerce.notification_service.service.impl.*;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "order-created", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeOrderCreatedEvent(OrderCreatedEvent event) {
        log.info("Received order-created event for orderId: {}", event.getOrderId());
        notificationService.processOrderCreated(event);
    }

    @KafkaListener(topics = "payment-completed", groupId = "${spring.kafka.consumer.group-id}")
    public void consumePaymentCompletedEvent(PaymentCompletedEvent event) {
        log.info("Received payment-completed event for orderId: {}", event.getOrderId());
        notificationService.processPaymentCompleted(event);
    }
}

