package com.ecommerce.product_service.kafka.consumer;

import com.ecommerce.product_service.kafka.event.OrderCreatedEvent;
import com.ecommerce.product_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final InventoryService inventoryService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "order-created", groupId = "${spring.kafka.consumer.group-id}")
    public void handleOrderCreated(String payload) {
        
        OrderCreatedEvent event = null;
        try {
            event = objectMapper.readValue(payload, OrderCreatedEvent.class);
        } catch (Exception e) {
            log.error("Failed to parse OrderCreatedEvent from payload: {}", payload, e);
            return;
        }

        if (event == null) {
            log.warn("Received null OrderCreatedEvent.");
            return;
        }

        if (event.getItems() == null || event.getItems().isEmpty()) {
            log.warn(
                    "Order {} contains no items. Inventory update skipped.",
                    event.getOrderId());
            return;
        }

        log.info(
                "Received order-created event. OrderId={}, Items={}",
                event.getOrderId(),
                event.getItems().size());

        for (OrderCreatedEvent.OrderItemDto item : event.getItems()) {

            try {

                inventoryService.decrementInventory(
                        item.getProductId(),
                        item.getQuantity());

                log.info(
                        "Inventory updated successfully. ProductId={}, Quantity={}",
                        item.getProductId(),
                        item.getQuantity());

            } catch (Exception ex) {

                log.error(
                        "Failed to update inventory. OrderId={}, ProductId={}, Quantity={}",
                        event.getOrderId(),
                        item.getProductId(),
                        item.getQuantity(),
                        ex);

            }
        }

        log.info(
                "Inventory processing completed for OrderId={}",
                event.getOrderId());
    }
}