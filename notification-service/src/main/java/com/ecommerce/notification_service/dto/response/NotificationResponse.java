package com.ecommerce.notification_service.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NotificationResponse {
    private String id;
    private String orderId;
    private String eventType;
    private String message;
    private LocalDateTime createdAt;
}
