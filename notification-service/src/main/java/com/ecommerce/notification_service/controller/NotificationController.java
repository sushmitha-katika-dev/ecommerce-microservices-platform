package com.ecommerce.notification_service.controller;

import com.ecommerce.notification_service.entity.*;
import com.ecommerce.notification_service.repository.*;

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

    private final NotificationLogRepository notificationLogRepository;

    @GetMapping
    public ResponseEntity<List<NotificationLog>> getAllNotificationLogs() {
        return ResponseEntity.ok(notificationLogRepository.findAll());
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<NotificationLog>> getNotificationLogsByOrderId(@PathVariable String orderId) {
        return ResponseEntity.ok(notificationLogRepository.findByOrderId(orderId));
    }
}
