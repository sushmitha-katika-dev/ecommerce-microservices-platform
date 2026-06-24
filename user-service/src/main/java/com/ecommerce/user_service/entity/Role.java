package com.ecommerce.user_service.entity;

import com.ecommerce.user_service.config.*;
import com.ecommerce.user_service.controller.*;
import com.ecommerce.user_service.entity.*;
import com.ecommerce.user_service.repository.*;
import com.ecommerce.user_service.security.*;
import com.ecommerce.user_service.service.*;
import com.ecommerce.user_service.dto.request.*;
import com.ecommerce.user_service.dto.response.*;
import com.ecommerce.user_service.service.impl.*;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(length = 36, updatable = false, nullable = false)
    private String id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Version
    private Integer version;
}

