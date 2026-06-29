package com.ecommerce.product_service.service;

import com.ecommerce.product_service.dto.request.ProductRequest;
import com.ecommerce.product_service.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(String id);

    ProductResponse createProduct(ProductRequest request);

    void deleteProduct(String id);
    
    ProductResponse updateProduct(String id, ProductRequest request);
}
