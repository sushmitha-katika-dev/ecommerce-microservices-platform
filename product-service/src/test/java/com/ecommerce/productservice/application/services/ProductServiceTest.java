package com.ecommerce.product_service.application.services;

import ProductRequest;
import ProductResponse;
import Category;
import Inventory;
import Product;
import DuplicateResourceException;
import ResourceNotFoundException;
import CategoryRepository;
import InventoryRepository;
import ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private ProductService productService;

    private Category category;
    private Product product;
    private ProductRequest request;

    @BeforeEach
    void setUp() {
        category = Category.builder()
                .id("cat-1")
                .name("Electronics")
                .slug("electronics")
                .build();

        product = Product.builder()
                .id("prod-1")
                .category(category)
                .name("Smartphone")
                .sku("SMART-001")
                .price(BigDecimal.valueOf(999.99))
                .active(true)
                .build();

        request = new ProductRequest();
        request.setCategoryId("cat-1");
        request.setName("Smartphone");
        request.setSku("SMART-001");
        request.setPrice(BigDecimal.valueOf(999.99));
        request.setActive(true);
    }

    @Test
    void createProduct_Success() {
        when(productRepository.existsBySku(anyString())).thenReturn(false);
        when(categoryRepository.findById(anyString())).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(new Inventory());

        ProductResponse response = productService.createProduct(request);

        assertNotNull(response);
        assertEquals("prod-1", response.getId());
        assertEquals("SMART-001", response.getSku());
        
        verify(productRepository, times(1)).save(any(Product.class));
        verify(inventoryRepository, times(1)).save(any(Inventory.class));
    }

    @Test
    void createProduct_DuplicateSku() {
        when(productRepository.existsBySku(anyString())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> {
            productService.createProduct(request);
        });

        verify(productRepository, never()).save(any(Product.class));
        verify(inventoryRepository, never()).save(any(Inventory.class));
    }

    @Test
    void createProduct_CategoryNotFound() {
        when(productRepository.existsBySku(anyString())).thenReturn(false);
        when(categoryRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            productService.createProduct(request);
        });

        verify(productRepository, never()).save(any(Product.class));
        verify(inventoryRepository, never()).save(any(Inventory.class));
    }
}
