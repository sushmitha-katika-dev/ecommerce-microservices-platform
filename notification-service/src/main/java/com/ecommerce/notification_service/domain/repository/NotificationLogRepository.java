package com.ecommerce.notification_service.domain.repository;

import com.ecommerce.notification_service.domain.model.NotificationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationLogRepository extends JpaRepository<NotificationLog, String> {
    List<NotificationLog> findByOrderId(String orderId);
}
