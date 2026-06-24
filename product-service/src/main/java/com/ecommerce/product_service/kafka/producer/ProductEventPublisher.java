package com.ecommerce.product_service.kafka.producer;

import com.ecommerce.product_service.kafka.event.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishProductEvent(ProductEvent event) {
        log.info("Publishing product event: {} for product: {}", event.getAction(), event.getProductId());
        kafkaTemplate.send("product-events", event);
    }
}
