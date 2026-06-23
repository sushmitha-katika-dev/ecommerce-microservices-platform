package com.ecommerce.cart_service.application.ports;

import com.ecommerce.cart_service.application.dtos.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "${product-service.url}/api/v1/products")
public interface ProductServiceClient {
    @GetMapping("/{id}")
    ProductResponse getProductById(@PathVariable("id") String id);
}
