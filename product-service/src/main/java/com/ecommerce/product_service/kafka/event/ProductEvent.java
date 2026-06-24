package com.ecommerce.product_service.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEvent {
    private String productId;
    private String name;
    private String action; // e.g., CREATED, UPDATED
}
