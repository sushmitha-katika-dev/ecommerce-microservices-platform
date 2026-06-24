package com.ecommerce.product_service.service.impl;

import com.ecommerce.product_service.entity.*;
import com.ecommerce.product_service.exception.*;
import com.ecommerce.product_service.repository.*;
import com.ecommerce.product_service.dto.request.*;
import com.ecommerce.product_service.dto.response.*;
import com.ecommerce.product_service.kafka.consumer.*;
import com.ecommerce.product_service.kafka.event.*;
import com.ecommerce.product_service.kafka.producer.*;
import com.ecommerce.product_service.service.impl.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

        private final ProductRepository productRepository;
        private final CategoryRepository categoryRepository;
        private final InventoryRepository inventoryRepository;
        private final ProductEventPublisher productEventPublisher;

        @Transactional(readOnly = true)
        public List<ProductResponse> getAllProducts() {
                return productRepository.findAll().stream()
                                .map(this::mapToResponse)
                                .collect(Collectors.toList());
        }

        @Transactional(readOnly = true)
        public ProductResponse getProductById(String id) {
                Product product = productRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
                return mapToResponse(product);
        }

        @Transactional
        public ProductResponse createProduct(ProductRequest request) {
                if (productRepository.existsBySku(request.getSku())) {
                        throw new DuplicateResourceException("Product with SKU already exists: " + request.getSku());
                }

                Category category = categoryRepository.findById(request.getCategoryId())
                                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

                Product product = Product.builder()
                                .category(category)
                                .name(request.getName())
                                .sku(request.getSku())
                                .description(request.getDescription())
                                .price(request.getPrice())
                                .active(request.isActive())
                                .build();

                Product savedProduct = productRepository.save(product);

                // Initialize inventory for the new product
                Inventory inventory = Inventory.builder()
                                .productId(savedProduct.getId())
                                .quantity(0)
                                .reservedQuantity(0)
                                .build();
                inventoryRepository.save(inventory);

                ProductEvent event = ProductEvent.builder()
                                .productId(savedProduct.getId())
                                .name(savedProduct.getName())
                                .action("CREATED")
                                .build();
                productEventPublisher.publishProductEvent(event);

                return mapToResponse(savedProduct);
        }

        @Transactional
        public void deleteProduct(String id) {
                Product product = productRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

                productRepository.deleteById(id);

                ProductEvent event = ProductEvent.builder()
                                .productId(id)
                                .name(product.getName())
                                .action("DELETED")
                                .build();
                productEventPublisher.publishProductEvent(event);
        }

        private ProductResponse mapToResponse(Product product) {
                return ProductResponse.builder()
                                .id(product.getId())
                                .categoryId(product.getCategory().getId())
                                .name(product.getName())
                                .sku(product.getSku())
                                .description(product.getDescription())
                                .price(product.getPrice())
                                .active(product.isActive())
                                .build();
        }
}
