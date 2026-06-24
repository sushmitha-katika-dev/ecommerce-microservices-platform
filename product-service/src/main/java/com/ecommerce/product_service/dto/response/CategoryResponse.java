package com.ecommerce.product_service.dto.response;

import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private String id;
    private String name;
    private String slug;
    private String parentId;
}
