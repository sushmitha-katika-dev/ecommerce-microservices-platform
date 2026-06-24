package com.ecommerce.order_service.kafka.event;

import com.ecommerce.order_service.controller.*;
import com.ecommerce.order_service.entity.*;
import com.ecommerce.order_service.exception.*;
import com.ecommerce.order_service.repository.*;
import com.ecommerce.order_service.service.*;
import com.ecommerce.order_service.dto.request.*;
import com.ecommerce.order_service.dto.response.*;
import com.ecommerce.order_service.kafka.event.*;
import com.ecommerce.order_service.kafka.producer.*;
import com.ecommerce.order_service.service.impl.*;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {
    private String orderId;
    private String userId;
    private BigDecimal amount;
    private List<OrderItemDto> items;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemDto {
        private String productId;
        private Integer quantity;
    }
}

