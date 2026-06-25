package com.ecommerce.notification_service.service;

import com.ecommerce.notification_service.kafka.event.OrderCreatedEvent;
import com.ecommerce.notification_service.kafka.event.PaymentCompletedEvent;

public interface NotificationService {
    void processOrderCreated(OrderCreatedEvent event);

    void processPaymentCompleted(PaymentCompletedEvent event);
}
