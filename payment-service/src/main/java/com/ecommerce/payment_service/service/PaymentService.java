package com.ecommerce.payment_service.service;

import com.ecommerce.payment_service.dto.response.PaymentResponse;
import com.ecommerce.payment_service.kafka.event.OrderCreatedEvent;

import java.util.List;

public interface PaymentService {
    void processPayment(OrderCreatedEvent event);

    List<PaymentResponse> getAllPayments();

    List<PaymentResponse> getPaymentsByOrderId(String orderId);

    List<PaymentResponse> getPaymentsByUserId(String userId);

    PaymentResponse getPaymentById(String id);
}
