package com.ecommerce.product_service.service;

import com.ecommerce.product_service.dto.request.CategoryRequest;
import com.ecommerce.product_service.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(String id);

    CategoryResponse createCategory(CategoryRequest request);

    void deleteCategory(String id);
}
