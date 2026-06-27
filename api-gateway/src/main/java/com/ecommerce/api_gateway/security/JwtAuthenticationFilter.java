package com.ecommerce.api_gateway.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtValidator jwtValidator;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }

        if (token != null && jwtValidator.validateToken(token)) {
            var claims = jwtValidator.extractAllClaims(token);
            String subject = claims.getSubject();
            
            if (subject != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                List<String> roles = claims.get("roles", List.class);
                List<SimpleGrantedAuthority> authorities = (roles != null) ? 
                    roles.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList()) : 
                    Collections.emptyList();

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        subject, null, authorities);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        
        filterChain.doFilter(request, response);
    }
}
