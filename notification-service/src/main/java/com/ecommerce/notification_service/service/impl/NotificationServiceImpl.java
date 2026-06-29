package com.ecommerce.notification_service.service.impl;

import com.ecommerce.notification_service.dto.response.NotificationResponse;
import com.ecommerce.notification_service.entity.NotificationLog;
import com.ecommerce.notification_service.kafka.event.OrderCreatedEvent;
import com.ecommerce.notification_service.kafka.event.PaymentCompletedEvent;
import com.ecommerce.notification_service.mapper.NotificationMapper;
import com.ecommerce.notification_service.repository.NotificationRepository;
import com.ecommerce.notification_service.service.EmailService;
import com.ecommerce.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final EmailService emailService;
    private final NotificationMapper notificationMapper;

    @Override
    public void processOrderCreated(OrderCreatedEvent event) {
        log.info("Processing order created event for order: {}", event.getOrderId());
        NotificationLog notification = NotificationLog.builder()
                .orderId(event.getOrderId())
                .eventType("ORDER_CREATED")
                .message("Order created successfully")
                .build();
        notificationRepository.save(notification);
        emailService.sendEmail("user@example.com", "Order Created", "Your order " + event.getOrderId() + " has been created.");
    }

    @Override
    public void processPaymentCompleted(PaymentCompletedEvent event) {
        log.info("Processing payment completed event for order: {}", event.getOrderId());
        NotificationLog notification = NotificationLog.builder()
                .orderId(event.getOrderId())
                .eventType("PAYMENT_COMPLETED")
                .message("Payment completed successfully")
                .build();
        notificationRepository.save(notification);
        emailService.sendEmail("user@example.com", "Payment Completed", "Payment for order " + event.getOrderId() + " was successful.");
    }

    @Override
    public List<NotificationResponse> getAllNotifications() {
        return notificationRepository.findAll().stream()
                .map(notificationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationResponse> getNotificationsByOrderId(String orderId) {
        return notificationRepository.findByOrderId(orderId).stream()
                .map(notificationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public NotificationResponse getNotificationById(String id) {
        NotificationLog notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        return notificationMapper.toResponse(notification);
    }
}
