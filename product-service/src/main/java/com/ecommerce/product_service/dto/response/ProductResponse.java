package com.ecommerce.product_service.dto.response;

import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse implements Serializable {
    private String id;
    private String categoryId;
    private String name;
    private String sku;
    private String description;
    private BigDecimal price;
    private boolean active;
}
