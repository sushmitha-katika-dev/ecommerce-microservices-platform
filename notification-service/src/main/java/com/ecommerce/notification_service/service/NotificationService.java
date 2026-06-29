package com.ecommerce.notification_service.service;

import com.ecommerce.notification_service.kafka.event.OrderCreatedEvent;
import com.ecommerce.notification_service.kafka.event.PaymentCompletedEvent;

import java.util.List;
import com.ecommerce.notification_service.dto.response.NotificationResponse;

public interface NotificationService {
    void processOrderCreated(OrderCreatedEvent event);

    void processPaymentCompleted(PaymentCompletedEvent event);

    List<NotificationResponse> getAllNotifications();

    List<NotificationResponse> getNotificationsByOrderId(String orderId);

    NotificationResponse getNotificationById(String id);
}
