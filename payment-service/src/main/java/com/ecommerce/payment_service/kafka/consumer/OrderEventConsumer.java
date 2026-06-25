package com.ecommerce.payment_service.kafka.consumer;

import com.ecommerce.payment_service.kafka.event.OrderCreatedEvent;
import com.ecommerce.payment_service.service.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final PaymentService paymentService;
    private final com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    @KafkaListener(topics = "order-created", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeOrderCreatedEvent(String eventPayload) {
        try {
            OrderCreatedEvent event = objectMapper.readValue(eventPayload, OrderCreatedEvent.class);
            log.info("Received order-created event for orderId: {}", event.getOrderId());
            paymentService.processPayment(event);
        } catch (Exception e) {
            log.error("Failed to deserialize event: {}", e.getMessage());
        }
    }
}
