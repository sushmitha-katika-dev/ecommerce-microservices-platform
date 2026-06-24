package com.ecommerce.user_service.dto.request;

import com.ecommerce.user_service.config.*;
import com.ecommerce.user_service.controller.*;
import com.ecommerce.user_service.entity.*;
import com.ecommerce.user_service.repository.*;
import com.ecommerce.user_service.security.*;
import com.ecommerce.user_service.service.*;
import com.ecommerce.user_service.dto.request.*;
import com.ecommerce.user_service.dto.response.*;
import com.ecommerce.user_service.service.impl.*;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
}

