package com.ecommerce.user_service.controller;

import com.ecommerce.user_service.dto.response.*;
import com.ecommerce.user_service.service.*;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponse> getMyProfile(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(userService.getUserProfile(email));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<java.util.List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @org.springframework.web.bind.annotation.PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> updateUser(@org.springframework.web.bind.annotation.PathVariable String id, @jakarta.validation.Valid @org.springframework.web.bind.annotation.RequestBody com.ecommerce.user_service.dto.request.UpdateUserRequest request) {
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    @org.springframework.web.bind.annotation.DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@org.springframework.web.bind.annotation.PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
