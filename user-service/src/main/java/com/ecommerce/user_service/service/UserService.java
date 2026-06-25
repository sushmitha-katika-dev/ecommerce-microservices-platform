package com.ecommerce.user_service.service;

import com.ecommerce.user_service.dto.response.UserResponse;

public interface UserService {
    UserResponse getUserProfile(String email);
}
