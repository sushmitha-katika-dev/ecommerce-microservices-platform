package com.ecommerce.payment_service.kafka.consumer;

import com.ecommerce.payment_service.kafka.event.*;
import com.ecommerce.payment_service.service.impl.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final PaymentService paymentService;

    @KafkaListener(topics = "order-created", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeOrderCreatedEvent(OrderCreatedEvent event) {
        log.info("Received order-created event for orderId: {}", event.getOrderId());
        paymentService.processPayment(event);
    }
}
