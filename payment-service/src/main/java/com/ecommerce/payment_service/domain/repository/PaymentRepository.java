package com.ecommerce.payment_service.domain.repository;

import com.ecommerce.payment_service.domain.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    List<Payment> findByOrderId(String orderId);
    List<Payment> findByUserId(String userId);
}
