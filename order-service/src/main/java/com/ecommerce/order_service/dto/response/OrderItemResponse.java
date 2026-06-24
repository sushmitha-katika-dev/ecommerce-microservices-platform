package com.ecommerce.order_service.dto.response;

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


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderItemResponse {
    private String id;
    private String productId;
    private Integer quantity;
    private BigDecimal unitPrice;
}

