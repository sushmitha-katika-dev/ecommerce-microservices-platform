package com.ecommerce.order_service.kafka.consumer;

import com.ecommerce.order_service.enums.OrderStatus;
import com.ecommerce.order_service.kafka.event.PaymentCompletedEvent;
import com.ecommerce.order_service.kafka.event.PaymentFailedEvent;
import com.ecommerce.order_service.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventConsumer {

    private final OrderService orderService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "payment-completed", groupId = "${spring.kafka.consumer.group-id}")
    public void consumePaymentCompletedEvent(String payload) {
        try {
            PaymentCompletedEvent event = objectMapper.readValue(payload, PaymentCompletedEvent.class);
            log.info("Received payment-completed event for orderId={}", event.getOrderId());
            orderService.updateOrderStatus(event.getOrderId(), OrderStatus.PAID);
        } catch (Exception ex) {
            log.error("Failed to process payment-completed event", ex);
        }
    }

    @KafkaListener(topics = "payment-failed", groupId = "${spring.kafka.consumer.group-id}")
    public void consumePaymentFailedEvent(String payload) {
        try {
            PaymentFailedEvent event = objectMapper.readValue(payload, PaymentFailedEvent.class);
            log.info("Received payment-failed event for orderId={}, reason={}", event.getOrderId(), event.getReason());
            orderService.updateOrderStatus(event.getOrderId(), OrderStatus.CANCELLED);
        } catch (Exception ex) {
            log.error("Failed to process payment-failed event", ex);
        }
    }
}
