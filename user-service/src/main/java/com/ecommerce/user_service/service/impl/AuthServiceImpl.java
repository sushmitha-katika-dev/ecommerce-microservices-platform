package com.ecommerce.user_service.service.impl;

import com.ecommerce.user_service.entity.*;
import com.ecommerce.user_service.repository.*;
import com.ecommerce.user_service.service.*;
import com.ecommerce.user_service.security.CustomUserDetails;
import com.ecommerce.user_service.dto.request.*;
import com.ecommerce.user_service.dto.response.*;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

        private final UserRepository userRepository;
        private final RoleRepository roleRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;

        @Override
        @Transactional
        public AuthResponse register(RegisterRequest request) {
                if (userRepository.existsByEmail(request.getEmail())) {
                        throw new IllegalArgumentException("Email already in use");
                }

                Role userRole = roleRepository.findByName("CUSTOMER")
                                .orElseGet(() -> roleRepository.save(Role.builder().name("CUSTOMER").build()));

                var user = User.builder()
                                .firstName(request.getFirstName())
                                .lastName(request.getLastName())
                                .email(request.getEmail())
                                .passwordHash(passwordEncoder.encode(request.getPassword()))
                                .phoneNumber(request.getPhoneNumber())
                                .gender(request.getGender())
                                .status("ACTIVE")
                                .roles(new HashSet<>())
                                .addresses(new java.util.ArrayList<>())
                                .build();
                user.getRoles().add(userRole);
                if (request.getAddresses() != null) {
                        for (AddressRequest addrReq : request.getAddresses()) {
                                Address addr = Address.builder()
                                                .user(user)
                                                .type(addrReq.getType())
                                                .street(addrReq.getStreet())
                                                .city(addrReq.getCity())
                                                .state(addrReq.getState())
                                                .zipCode(addrReq.getZipCode())
                                                .country(addrReq.getCountry())
                                                .build();
                                user.getAddresses().add(addr);
                        }
                }
                user.getRoles().add(userRole);

                userRepository.saveAndFlush(user);

                CustomUserDetails userDetails = new CustomUserDetails(user);
                var jwtToken = jwtService.generateToken(userDetails);

                return AuthResponse.builder()
                                .token(jwtToken)
                                .user(mapToResponse(user))
                                .build();
        }

        @Override
        public AuthResponse login(LoginRequest request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getEmail(),
                                                request.getPassword()));

                var user = userRepository.findByEmail(request.getEmail())
                                .orElseThrow();

                CustomUserDetails userDetails = new CustomUserDetails(user);
                var jwtToken = jwtService.generateToken(userDetails);

                return AuthResponse.builder()
                                .token(jwtToken)
                                .user(mapToResponse(user))
                                .build();
        }

        private UserResponse mapToResponse(User user) {
                var addressResponses = user.getAddresses() != null ? user.getAddresses().stream().map(a -> AddressResponse.builder()
                        .id(a.getId())
                        .type(a.getType())
                        .street(a.getStreet())
                        .city(a.getCity())
                        .state(a.getState())
                        .zipCode(a.getZipCode())
                        .country(a.getCountry())
                        .build()).collect(Collectors.toList()) : new java.util.ArrayList<AddressResponse>();

                return UserResponse.builder()
                                .id(user.getId())
                                .email(user.getEmail())
                                .firstName(user.getFirstName())
                                .lastName(user.getLastName())
                                .phoneNumber(user.getPhoneNumber())
                                .gender(user.getGender())
                                .status(user.getStatus())
                                .roles(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()))
                                .addresses(addressResponses)
                                .createdAt(user.getCreatedAt())
                                .updatedAt(user.getUpdatedAt())
                                .build();
        }
}
