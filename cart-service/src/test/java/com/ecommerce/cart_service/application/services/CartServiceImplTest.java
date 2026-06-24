package com.ecommerce.cart_service.application.services;

import CartItemRequest;
import CartResponse;
import ProductResponse;
import ProductServiceClient;
import Cart;
import CartStatus;
import ProductValidationException;
import CartRepository;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductServiceClient productServiceClient;

    @InjectMocks
    private CartServiceImpl cartService;

    private Cart cart;
    private ProductResponse productResponse;

    @BeforeEach
    void setUp() {
        cart = new Cart();
        cart.setId("cart-1");
        cart.setSessionId("session-1");
        cart.setStatus(CartStatus.ACTIVE);
        cart.setItems(new ArrayList<>());

        productResponse = new ProductResponse();
        productResponse.setId("prod-1");
        productResponse.setName("Test Product");
        productResponse.setPrice(BigDecimal.valueOf(100));
        productResponse.setActive(true);
    }

    @Test
    void addItem_Success() {
        CartItemRequest request = new CartItemRequest();
        request.setProductId("prod-1");
        request.setQuantity(2);

        when(cartRepository.findBySessionId(anyString())).thenReturn(Optional.of(cart));
        when(productServiceClient.getProductById(anyString())).thenReturn(productResponse);
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        CartResponse response = cartService.addItem("session-1", request);

        assertNotNull(response);
        assertEquals("session-1", response.getSessionId());
        verify(productServiceClient, times(1)).getProductById("prod-1");
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void addItem_ProductNotFound() {
        CartItemRequest request = new CartItemRequest();
        request.setProductId("prod-1");
        request.setQuantity(2);

        when(cartRepository.findBySessionId(anyString())).thenReturn(Optional.of(cart));
        
        FeignException.NotFound feignException = mock(FeignException.NotFound.class);
        when(productServiceClient.getProductById(anyString())).thenThrow(feignException);

        assertThrows(ProductValidationException.class, () -> {
            cartService.addItem("session-1", request);
        });

        verify(cartRepository, never()).save(any(Cart.class));
    }

    @Test
    void addItem_ProductInactive() {
        CartItemRequest request = new CartItemRequest();
        request.setProductId("prod-1");
        request.setQuantity(2);
        
        productResponse.setActive(false);

        when(cartRepository.findBySessionId(anyString())).thenReturn(Optional.of(cart));
        when(productServiceClient.getProductById(anyString())).thenReturn(productResponse);

        assertThrows(ProductValidationException.class, () -> {
            cartService.addItem("session-1", request);
        });

        verify(cartRepository, never()).save(any(Cart.class));
    }
}
