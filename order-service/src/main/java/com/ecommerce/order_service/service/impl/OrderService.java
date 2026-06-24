package com.ecommerce.order_service.service.impl;

import com.ecommerce.order_service.entity.*;
import com.ecommerce.order_service.enums.OrderStatus;
import com.ecommerce.order_service.exception.*;
import com.ecommerce.order_service.repository.*;
import com.ecommerce.order_service.dto.request.*;
import com.ecommerce.order_service.dto.response.*;
import com.ecommerce.order_service.kafka.event.*;
import com.ecommerce.order_service.kafka.producer.*;

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

                        BigDecimal itemTotal = itemReq.getUnitPrice()
                                        .multiply(BigDecimal.valueOf(itemReq.getQuantity()));
                        totalAmount = totalAmount.add(itemTotal);

                        order.addItem(orderItem);
                }

                order.setTotalAmount(totalAmount);

                Order savedOrder = orderRepository.save(order);

                // Publish Event
                List<OrderCreatedEvent.OrderItemDto> eventItems = savedOrder.getItems().stream()
                                .map(item -> OrderCreatedEvent.OrderItemDto.builder()
                                                .productId(item.getProductId())
                                                .quantity(item.getQuantity())
                                                .build())
                                .collect(Collectors.toList());

                OrderCreatedEvent event = OrderCreatedEvent.builder()
                                .orderId(savedOrder.getId())
                                .userId(savedOrder.getUserId())
                                .amount(savedOrder.getTotalAmount())
                                .items(eventItems)
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
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Order not found with id: " + orderId));
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
