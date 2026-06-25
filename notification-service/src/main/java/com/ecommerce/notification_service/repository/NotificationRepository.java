package com.ecommerce.notification_service.repository;
import com.ecommerce.notification_service.entity.*;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationLog, String> {
    List<NotificationLog> findByOrderId(String orderId);
}
