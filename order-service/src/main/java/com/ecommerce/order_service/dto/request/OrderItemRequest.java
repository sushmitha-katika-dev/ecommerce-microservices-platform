package com.ecommerce.order_service.dto.request;

import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;

import lombok.Builder;

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


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequest {
    @NotBlank
    private String productId;
    
    @NotNull
    @Min(1)
    private Integer quantity;
    
    @NotNull
    private BigDecimal unitPrice;
}


