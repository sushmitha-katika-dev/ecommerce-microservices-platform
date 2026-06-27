package com.ecommerce.order_service.service;

import com.ecommerce.order_service.dto.request.OrderRequest;
import com.ecommerce.order_service.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse placeOrder(OrderRequest request);

    List<OrderResponse> getOrderHistory(String userId);

    OrderResponse getOrderById(String orderId);

    void updateOrderStatus(String orderId, com.ecommerce.order_service.enums.OrderStatus status);
}
