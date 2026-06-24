package com.ecommerce.cart_service.dto.response;

import com.ecommerce.cart_service.controller.*;
import com.ecommerce.cart_service.entity.*;
import com.ecommerce.cart_service.exception.*;
import com.ecommerce.cart_service.repository.*;
import com.ecommerce.cart_service.service.*;
import com.ecommerce.cart_service.dto.request.*;
import com.ecommerce.cart_service.dto.response.*;
import com.ecommerce.cart_service.kafka.event.*;
import com.ecommerce.cart_service.kafka.producer.*;
import com.ecommerce.cart_service.service.impl.*;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class CartItemResponse {
    private String productId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
}

