package com.ecommerce.product_service.repository;

import com.ecommerce.product_service.controller.*;
import com.ecommerce.product_service.entity.*;
import com.ecommerce.product_service.exception.*;
import com.ecommerce.product_service.repository.*;
import com.ecommerce.product_service.dto.request.*;
import com.ecommerce.product_service.dto.response.*;
import com.ecommerce.product_service.kafka.consumer.*;
import com.ecommerce.product_service.kafka.event.*;
import com.ecommerce.product_service.kafka.producer.*;
import com.ecommerce.product_service.service.impl.*;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {
    Optional<Category> findBySlug(String slug);
    boolean existsByNameOrSlug(String name, String slug);
}

