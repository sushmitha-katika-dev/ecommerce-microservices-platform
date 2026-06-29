package com.ecommerce.user_service.service.impl;

import com.ecommerce.user_service.entity.*;
import com.ecommerce.user_service.repository.*;
import com.ecommerce.user_service.dto.response.*;
import com.ecommerce.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse getUserProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return mapToResponse(user);
    }

    @Override
    public UserResponse updateUser(String id, com.ecommerce.user_service.dto.request.UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user = userRepository.save(user);
        return mapToResponse(user);
    }

    @Override
    public void deleteUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        userRepository.delete(user);
    }

    @Override
    public java.util.List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .status(user.getStatus())
                .roles(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()))
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
