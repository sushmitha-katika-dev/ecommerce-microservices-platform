package com.ecommerce.notification_service.entity;

import com.ecommerce.notification_service.controller.*;
import com.ecommerce.notification_service.entity.*;
import com.ecommerce.notification_service.repository.*;
import com.ecommerce.notification_service.kafka.consumer.*;
import com.ecommerce.notification_service.kafka.event.*;
import com.ecommerce.notification_service.service.impl.*;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String orderId;

    private String eventType;

    private String message;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}

