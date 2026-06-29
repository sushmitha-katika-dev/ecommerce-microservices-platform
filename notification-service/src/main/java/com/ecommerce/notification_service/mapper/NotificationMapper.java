package com.ecommerce.notification_service.mapper;

import com.ecommerce.notification_service.dto.response.NotificationResponse;
import com.ecommerce.notification_service.entity.NotificationLog;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public NotificationResponse toResponse(NotificationLog entity) {
        if (entity == null) {
            return null;
        }
        return NotificationResponse.builder()
                .id(entity.getId())
                .orderId(entity.getOrderId())
                .eventType(entity.getEventType())
                .message(entity.getMessage())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
