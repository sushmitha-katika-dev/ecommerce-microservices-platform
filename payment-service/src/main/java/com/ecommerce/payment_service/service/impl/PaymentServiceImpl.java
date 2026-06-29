package com.ecommerce.payment_service.service.impl;

import com.ecommerce.payment_service.entity.*;
import com.ecommerce.payment_service.enums.PaymentStatus;
import com.ecommerce.payment_service.repository.*;
import com.ecommerce.payment_service.dto.response.*;
import com.ecommerce.payment_service.kafka.event.*;
import com.ecommerce.payment_service.kafka.producer.*;
import com.ecommerce.payment_service.service.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import com.ecommerce.payment_service.mapper.PaymentMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentEventPublisher eventPublisher;
    private final PaymentMapper paymentMapper;

    @Override
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

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponse> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(paymentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponse> getPaymentsByOrderId(String orderId) {
        return paymentRepository.findByOrderId(orderId).stream()
                .map(paymentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponse> getPaymentsByUserId(String userId) {
        return paymentRepository.findByUserId(userId).stream()
                .map(paymentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getPaymentById(String id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        return paymentMapper.toResponse(payment);
    }
}
