package com.ecommerce.product_service.entity;

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


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "categories")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "parent_id")
    private String parentId;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Version
    private Integer version;
}

