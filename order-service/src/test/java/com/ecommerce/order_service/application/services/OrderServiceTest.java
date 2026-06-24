package com.ecommerce.order_service.application.services;

import OrderCreatedEvent;
import OrderItemRequest;
import OrderRequest;
import OrderResponse;
import Order;
import OrderStatus;
import OrderEventPublisher;
import OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderEventPublisher eventPublisher;

    @InjectMocks
    private OrderService orderService;

    private OrderRequest request;

    @BeforeEach
    void setUp() {
        OrderItemRequest item1 = new OrderItemRequest();
        item1.setProductId("prod-1");
        item1.setQuantity(2);
        item1.setUnitPrice(BigDecimal.valueOf(50.0));

        request = new OrderRequest();
        request.setUserId("user-1");
        request.setShippingAddress("123 Main St");
        request.setItems(List.of(item1));
    }

    @Test
    void placeOrder_Success() {
        Order savedOrder = Order.builder()
                .id("order-1")
                .userId("user-1")
                .status(OrderStatus.PENDING)
                .totalAmount(BigDecimal.valueOf(100.0))
                .build();
        
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);
        doNothing().when(eventPublisher).publishOrderCreatedEvent(any(OrderCreatedEvent.class));

        OrderResponse response = orderService.placeOrder(request);

        assertNotNull(response);
        assertEquals("order-1", response.getId());
        assertEquals(OrderStatus.PENDING, response.getStatus());

        verify(orderRepository, times(1)).save(any(Order.class));
        verify(eventPublisher, times(1)).publishOrderCreatedEvent(any(OrderCreatedEvent.class));
    }
}
