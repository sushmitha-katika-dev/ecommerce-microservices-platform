package com.ecommerce.product_service.kafka.event;

import com.ecommerce.product_service.controller.*;
import com.ecommerce.product_service.entity.*;
import com.ecommerce.product_service.exception.*;
import com.ecommerce.product_service.repository.*;
import com.ecommerce.product_service.dto.request.*;
import com.ecommerce.product_service.dto.response.*;
import com.ecommerce.product_service.kafka.consumer.*;
import com.ecommerce.product_service.kafka.event.*;
import com.ecommerce.product_service.kafka.producer.*;
import com.ecommerce.product_service.service.impl.*;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderCreatedEvent {
    private String orderId;
    private String userId;
    private BigDecimal amount;
    private List<OrderItemDto> items;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OrderItemDto {
        private String productId;
        private Integer quantity;
    }
}

