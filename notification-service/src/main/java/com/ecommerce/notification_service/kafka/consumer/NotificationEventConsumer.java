package com.ecommerce.notification_service.kafka.consumer;

import com.ecommerce.notification_service.kafka.event.*;
import com.ecommerce.notification_service.service.*;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventConsumer {

    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "order-created", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeOrderCreatedEvent(String payload) {
        try {
            OrderCreatedEvent event = objectMapper.readValue(payload, OrderCreatedEvent.class);

            log.info("Received order-created event for orderId={}", event.getOrderId());

            notificationService.processOrderCreated(event);

        } catch (Exception ex) {
            log.error("Failed to process order-created event", ex);
        }
    }

    @KafkaListener(topics = "payment-completed", groupId = "${spring.kafka.consumer.group-id}")
    public void consumePaymentCompletedEvent(String payload) {
        try {
            PaymentCompletedEvent event = objectMapper.readValue(payload, PaymentCompletedEvent.class);

            log.info("Received payment-completed event for orderId={}", event.getOrderId());

            notificationService.processPaymentCompleted(event);

        } catch (Exception ex) {
            log.error("Failed to process payment-completed event", ex);
        }
    }
}

