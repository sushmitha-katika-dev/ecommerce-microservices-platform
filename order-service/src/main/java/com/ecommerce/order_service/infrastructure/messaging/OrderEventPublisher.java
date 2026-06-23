package com.ecommerce.order_service.infrastructure.messaging;

import com.ecommerce.order_service.application.dtos.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "order-created";

    public void publishOrderCreatedEvent(OrderCreatedEvent event) {
        log.info("Publishing event to topic {}: {}", TOPIC, event);
        kafkaTemplate.send(TOPIC, event.getOrderId(), event);
    }
}
