package com.ecommerce.notification_service.controller;

import com.ecommerce.notification_service.entity.NotificationLog;
import com.ecommerce.notification_service.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationRepository notificationRepository;

    @GetMapping
    public ResponseEntity<List<NotificationLog>> getAllNotificationLogs() {
        return ResponseEntity.ok(notificationRepository.findAll());
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<NotificationLog>> getNotificationLogsByOrderId(@PathVariable String orderId) {
        return ResponseEntity.ok(notificationRepository.findByOrderId(orderId));
    }
}
