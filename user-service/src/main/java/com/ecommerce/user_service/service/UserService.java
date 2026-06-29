package com.ecommerce.user_service.service;

import com.ecommerce.user_service.dto.response.UserResponse;

import java.util.List;
import com.ecommerce.user_service.dto.request.UpdateUserRequest;

public interface UserService {
    UserResponse getUserProfile(String email);
    UserResponse updateUser(String id, UpdateUserRequest request);
    void deleteUser(String id);
    List<UserResponse> getAllUsers();
}
