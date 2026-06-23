package com.ecommerce.order_service.application.services;

import com.ecommerce.order_service.application.dtos.OrderCreatedEvent;
import com.ecommerce.order_service.application.dtos.OrderItemResponse;
import com.ecommerce.order_service.application.dtos.OrderRequest;
import com.ecommerce.order_service.application.dtos.OrderResponse;
import com.ecommerce.order_service.domain.entities.Order;
import com.ecommerce.order_service.domain.entities.OrderItem;
import com.ecommerce.order_service.domain.entities.OrderStatus;
import com.ecommerce.order_service.domain.exceptions.ResourceNotFoundException;
import com.ecommerce.order_service.infrastructure.messaging.OrderEventPublisher;
import com.ecommerce.order_service.infrastructure.persistence.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventPublisher eventPublisher;

    @Transactional
    public OrderResponse placeOrder(OrderRequest request) {
        Order order = Order.builder()
                .userId(request.getUserId())
                .status(OrderStatus.PENDING)
                .shippingAddress(request.getShippingAddress())
                .build();

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (var itemReq : request.getItems()) {
            OrderItem orderItem = OrderItem.builder()
                    .productId(itemReq.getProductId())
                    .quantity(itemReq.getQuantity())
                    .unitPrice(itemReq.getUnitPrice())
                    .build();
            
            BigDecimal itemTotal = itemReq.getUnitPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);
            
            order.addItem(orderItem);
        }

        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        // Publish Event
        OrderCreatedEvent event = OrderCreatedEvent.builder()
                .orderId(savedOrder.getId())
                .userId(savedOrder.getUserId())
                .amount(savedOrder.getTotalAmount())
                .build();
        eventPublisher.publishOrderCreatedEvent(event);

        return mapToResponse(savedOrder);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getOrderHistory(String userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrderById(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
        return mapToResponse(order);
    }

    private OrderResponse mapToResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(item -> OrderItemResponse.builder()
                        .id(item.getId())
                        .productId(item.getProductId())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .build())
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .shippingAddress(order.getShippingAddress())
                .createdAt(order.getCreatedAt())
                .items(itemResponses)
                .build();
    }
}
