package com.ecommerce.order_service.kafka.consumer;

import com.ecommerce.order_service.dto.request.OrderItemRequest;
import com.ecommerce.order_service.dto.request.OrderRequest;
import com.ecommerce.order_service.kafka.event.CartCheckoutEvent;
import com.ecommerce.order_service.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class CartCheckoutEventConsumer {

    private final OrderService orderService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "cart-checkout", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeCartCheckoutEvent(String payload) {
        try {
            CartCheckoutEvent event = objectMapper.readValue(payload, CartCheckoutEvent.class);
            log.info("Received cart-checkout event for userId={}", event.getUserId());

            List<OrderItemRequest> orderItems = event.getItems().stream()
                    .map(item -> OrderItemRequest.builder()
                            .productId(item.getProductId())
                            .quantity(item.getQuantity())
                            .unitPrice(item.getUnitPrice())
                            .build())
                    .collect(Collectors.toList());

            OrderRequest orderRequest = OrderRequest.builder()
                    .userId(event.getUserId())
                    .shippingAddress("Address pending or retrieved from profile")
                    .items(orderItems)
                    .build();

            orderService.placeOrder(orderRequest);
            log.info("Order successfully placed for userId={}", event.getUserId());

        } catch (Exception ex) {
            log.error("Failed to process cart-checkout event", ex);
        }
    }
}
