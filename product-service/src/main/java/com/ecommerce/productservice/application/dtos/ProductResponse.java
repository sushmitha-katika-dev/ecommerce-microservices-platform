package com.ecommerce.productservice.application.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductResponse {
    private String id;
    private String categoryId;
    private String name;
    private String sku;
    private String description;
    private BigDecimal price;
    private boolean active;
}
