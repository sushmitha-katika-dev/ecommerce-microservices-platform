package com.ecommerce.cart_service.kafka.producer;

import com.ecommerce.cart_service.controller.*;
import com.ecommerce.cart_service.entity.*;
import com.ecommerce.cart_service.exception.*;
import com.ecommerce.cart_service.repository.*;
import com.ecommerce.cart_service.service.*;
import com.ecommerce.cart_service.dto.request.*;
import com.ecommerce.cart_service.dto.response.*;
import com.ecommerce.cart_service.kafka.event.*;
import com.ecommerce.cart_service.kafka.producer.*;
import com.ecommerce.cart_service.service.impl.*;


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

