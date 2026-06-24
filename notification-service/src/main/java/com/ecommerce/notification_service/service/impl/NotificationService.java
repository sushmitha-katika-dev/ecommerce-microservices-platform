package com.ecommerce.notification_service.service.impl;

import com.ecommerce.notification_service.controller.*;
import com.ecommerce.notification_service.entity.*;
import com.ecommerce.notification_service.repository.*;
import com.ecommerce.notification_service.kafka.consumer.*;
import com.ecommerce.notification_service.kafka.event.*;
import com.ecommerce.notification_service.service.impl.*;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final EmailService emailService;
    private final NotificationLogRepository notificationLogRepository;

    @Transactional
    public void processOrderCreated(OrderCreatedEvent event) {
        log.info("Processing order-created notification for orderId: {}", event.getOrderId());
        
        String emailTo = event.getUserId() + "@example.com"; // Mock email based on userId
        String emailSubject = "Order Confirmation: " + event.getOrderId();
        String emailBody = String.format("Dear customer,\n\nYour order %s has been created successfully.\nTotal Amount: $%s\n\nThank you for shopping with us!", 
                event.getOrderId(), event.getAmount());

        emailService.sendEmail(emailTo, emailSubject, emailBody);

        NotificationLog auditLog = NotificationLog.builder()
                .orderId(event.getOrderId())
                .eventType("ORDER_CREATED")
                .message("Order confirmation email sent to " + emailTo)
                .build();
        
        notificationLogRepository.save(auditLog);
    }

    @Transactional
    public void processPaymentCompleted(PaymentCompletedEvent event) {
        log.info("Processing payment-completed notification for orderId: {}", event.getOrderId());
        
        // In a real scenario, you'd fetch user details by userId. Here we just mock it.
        String emailTo = "customer_" + event.getOrderId() + "@example.com"; 
        String emailSubject = "Payment Receipt for Order: " + event.getOrderId();
        String emailBody = String.format("Dear customer,\n\nWe have received your payment for order %s.\nPayment ID: %s\nStatus: %s\n\nThank you!", 
                event.getOrderId(), event.getPaymentId(), event.getStatus());

        emailService.sendEmail(emailTo, emailSubject, emailBody);

        NotificationLog auditLog = NotificationLog.builder()
                .orderId(event.getOrderId())
                .eventType("PAYMENT_COMPLETED")
                .message("Payment receipt email sent to " + emailTo)
                .build();
        
        notificationLogRepository.save(auditLog);
    }
}

