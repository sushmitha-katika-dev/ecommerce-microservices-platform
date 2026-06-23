package com.ecommerce.cart_service.application.services;

import com.ecommerce.cart_service.application.dtos.CartItemRequest;
import com.ecommerce.cart_service.application.dtos.CartItemResponse;
import com.ecommerce.cart_service.application.dtos.CartResponse;
import com.ecommerce.cart_service.application.dtos.ProductResponse;
import com.ecommerce.cart_service.application.ports.ProductServiceClient;
import com.ecommerce.cart_service.domain.entities.Cart;
import com.ecommerce.cart_service.domain.entities.CartItem;
import com.ecommerce.cart_service.domain.entities.CartStatus;
import com.ecommerce.cart_service.domain.exceptions.ProductValidationException;
import com.ecommerce.cart_service.infrastructure.persistence.CartRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductServiceClient productServiceClient;

    @Override
    @Transactional(readOnly = true)
    public CartResponse getCart(String sessionId) {
        Cart cart = getOrCreateCart(sessionId);
        return mapToResponse(cart);
    }

    @Override
    @Transactional
    public CartResponse addItem(String sessionId, CartItemRequest request) {
        Cart cart = getOrCreateCart(sessionId);
        
        // Validate product via Feign Client
        ProductResponse product;
        try {
            product = productServiceClient.getProductById(request.getProductId());
            if (!product.isActive()) {
                throw new ProductValidationException("Product is not active");
            }
        } catch (FeignException.NotFound e) {
            throw new ProductValidationException("Product not found");
        }

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(request.getProductId()))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + request.getQuantity());
        } else {
            CartItem newItem = CartItem.builder()
                    .productId(product.getId())
                    .quantity(request.getQuantity())
                    .unitPrice(product.getPrice())
                    .build();
            cart.addItem(newItem);
        }

        Cart savedCart = cartRepository.save(cart);
        return mapToResponse(savedCart);
    }

    @Override
    @Transactional
    public CartResponse removeItem(String sessionId, String productId) {
        Cart cart = getOrCreateCart(sessionId);
        
        cart.getItems().removeIf(item -> item.getProductId().equals(productId));
        
        Cart savedCart = cartRepository.save(cart);
        return mapToResponse(savedCart);
    }

    private Cart getOrCreateCart(String sessionId) {
        return cartRepository.findBySessionId(sessionId)
                .orElseGet(() -> cartRepository.save(Cart.builder()
                        .sessionId(sessionId)
                        .status(CartStatus.ACTIVE)
                        .build()));
    }

    private CartResponse mapToResponse(Cart cart) {
        List<CartItemResponse> itemResponses = cart.getItems().stream()
                .map(item -> CartItemResponse.builder()
                        .productId(item.getProductId())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .subtotal(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                        .build())
                .collect(Collectors.toList());

        BigDecimal total = itemResponses.stream()
                .map(CartItemResponse::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return CartResponse.builder()
                .id(cart.getId())
                .sessionId(cart.getSessionId())
                .items(itemResponses)
                .totalAmount(total)
                .build();
    }
}
