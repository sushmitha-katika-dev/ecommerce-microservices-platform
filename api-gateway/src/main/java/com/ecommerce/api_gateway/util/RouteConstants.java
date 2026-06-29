package com.ecommerce.api_gateway.util;

public final class RouteConstants {

    private RouteConstants() {
        // Prevent instantiation
    }

    public static final String[] PUBLIC_ROUTES = {
            "/api/users/auth/login",
            "/api/users/auth/register",
            "/v3/api-docs/**",
            "/api/*/v3/api-docs",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };

    public static final String[] PROTECTED_ROUTES = {
            "/api/carts/**",
            "/api/orders/**",
            "/api/payments/**",
            "/api/notifications/**"
    };

    public static final String[] PRODUCT_ROUTES = {
            "/api/products/**"
    };
}
