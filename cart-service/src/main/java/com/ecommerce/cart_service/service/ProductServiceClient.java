package com.ecommerce.cart_service.service;

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


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "${product-service.url}/api/v1/products")
public interface ProductServiceClient {
    @GetMapping("/{id}")
    ProductResponse getProductById(@PathVariable("id") String id);
}

