package com.ecommerce.cart_service.kafka.producer;

import com.ecommerce.cart_service.kafka.event.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CartEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishCartCheckoutEvent(CartCheckoutEvent event) {
        log.info("Publishing cart checkout event for user: {}", event.getUserId());
        kafkaTemplate.send("cart-checkout", event);
    }
}
