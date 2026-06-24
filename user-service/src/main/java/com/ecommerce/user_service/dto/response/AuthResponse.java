package com.ecommerce.user_service.dto.response;

import com.ecommerce.user_service.config.*;
import com.ecommerce.user_service.controller.*;
import com.ecommerce.user_service.entity.*;
import com.ecommerce.user_service.repository.*;
import com.ecommerce.user_service.security.*;
import com.ecommerce.user_service.service.*;
import com.ecommerce.user_service.dto.request.*;
import com.ecommerce.user_service.dto.response.*;
import com.ecommerce.user_service.service.impl.*;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private UserResponse user;
}

