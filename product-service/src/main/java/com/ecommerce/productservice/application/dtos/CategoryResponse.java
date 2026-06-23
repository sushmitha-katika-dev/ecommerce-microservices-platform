package com.ecommerce.productservice.application.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryResponse {
    private String id;
    private String name;
    private String slug;
    private String parentId;
}
