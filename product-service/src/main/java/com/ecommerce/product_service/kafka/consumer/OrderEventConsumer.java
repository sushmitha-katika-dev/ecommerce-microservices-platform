package com.ecommerce.product_service.kafka.consumer;

import com.ecommerce.product_service.controller.*;
import com.ecommerce.product_service.entity.*;
import com.ecommerce.product_service.exception.*;
import com.ecommerce.product_service.repository.*;
import com.ecommerce.product_service.dto.request.*;
import com.ecommerce.product_service.dto.response.*;
import com.ecommerce.product_service.kafka.consumer.*;
import com.ecommerce.product_service.kafka.event.*;
import com.ecommerce.product_service.kafka.producer.*;
import com.ecommerce.product_service.service.impl.*;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final InventoryService inventoryService;

    @KafkaListener(topics = "order-created", groupId = "product-service-group")
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("Received order-created event for order: {}", event.getOrderId());
        if (event.getItems() != null) {
            for (OrderCreatedEvent.OrderItemDto item : event.getItems()) {
                try {
                    inventoryService.decrementInventory(item.getProductId(), item.getQuantity());
                    log.info("Decremented inventory for product: {} by {}", item.getProductId(), item.getQuantity());
                } catch (Exception e) {
                    log.error("Failed to decrement inventory for product: {}", item.getProductId(), e);
                }
            }
        } else {
            log.warn("Order event missing items, cannot decrement inventory.");
        }
    }
}

