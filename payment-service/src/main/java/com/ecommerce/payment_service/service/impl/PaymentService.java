package com.ecommerce.payment_service.service.impl;

import com.ecommerce.payment_service.entity.*;
import com.ecommerce.payment_service.enums.PaymentStatus;
import com.ecommerce.payment_service.repository.*;
import com.ecommerce.payment_service.dto.response.*;
import com.ecommerce.payment_service.kafka.event.*;
import com.ecommerce.payment_service.kafka.producer.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentEventPublisher eventPublisher;

    @Transactional
    public void processPayment(OrderCreatedEvent event) {
        log.info("Processing payment for orderId: {}", event.getOrderId());

        Payment payment = Payment.builder()
                .orderId(event.getOrderId())
                .userId(event.getUserId())
                .amount(event.getAmount())
                .status(PaymentStatus.PENDING)
                .build();

        payment = paymentRepository.save(payment);

        try {
            // Mock Payment Processing
            // 80% chance of success
            boolean isSuccess = Math.random() < 0.8;

            if (isSuccess) {
                payment.setStatus(PaymentStatus.SUCCESS);
                paymentRepository.save(payment);

                PaymentCompletedEvent completedEvent = PaymentCompletedEvent.builder()
                        .orderId(payment.getOrderId())
                        .paymentId(payment.getId())
                        .status(PaymentStatus.SUCCESS.name())
                        .build();

                eventPublisher.publishPaymentCompletedEvent(completedEvent);
                log.info("Payment successful for orderId: {}", payment.getOrderId());
            } else {
                payment.setStatus(PaymentStatus.FAILED);
                paymentRepository.save(payment);

                PaymentFailedEvent failedEvent = PaymentFailedEvent.builder()
                        .orderId(payment.getOrderId())
                        .paymentId(payment.getId())
                        .reason("Payment gateway rejected the transaction")
                        .build();

                eventPublisher.publishPaymentFailedEvent(failedEvent);
                log.warn("Payment failed for orderId: {}", payment.getOrderId());
            }

        } catch (Exception e) {
            log.error("Error processing payment for orderId: {}", event.getOrderId(), e);
            payment.setStatus(PaymentStatus.FAILED);
            paymentRepository.save(payment);

            PaymentFailedEvent failedEvent = PaymentFailedEvent.builder()
                    .orderId(payment.getOrderId())
                    .paymentId(payment.getId())
                    .reason("Internal server error during payment processing")
                    .build();

            eventPublisher.publishPaymentFailedEvent(failedEvent);
        }
    }

    @Transactional(readOnly = true)
    public List<PaymentResponse> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PaymentResponse> getPaymentsByOrderId(String orderId) {
        return paymentRepository.findByOrderId(orderId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PaymentResponse> getPaymentsByUserId(String userId) {
        return paymentRepository.findByUserId(userId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private PaymentResponse mapToResponse(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .orderId(payment.getOrderId())
                .userId(payment.getUserId())
                .amount(payment.getAmount())
                .status(payment.getStatus())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .build();
    }
}
