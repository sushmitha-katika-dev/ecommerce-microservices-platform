package com.ecommerce.product_service.dto.request;

import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;

import lombok.Builder;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String slug;
    private String parentId;
}
