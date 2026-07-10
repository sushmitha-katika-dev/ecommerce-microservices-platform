package com.ecommerce.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;
import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;
import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.addRequestHeader;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class GatewayConfig {

    @Value("${USER_SERVICE_URL:http://localhost:8081}")
    private String userServiceUrl;

    @Value("${PRODUCT_SERVICE_URL:http://localhost:8082}")
    private String productServiceUrl;
    
    @Value("${CART_SERVICE_URL:http://localhost:8083}")
    private String cartServiceUrl;

    @Value("${ORDER_SERVICE_URL:http://localhost:8084}")
    private String orderServiceUrl;

    @Value("${PAYMENT_SERVICE_URL:http://localhost:8085}")
    private String paymentServiceUrl;

    @Value("${NOTIFICATION_SERVICE_URL:http://localhost:8086}")
    private String notificationServiceUrl;

    @Bean
    public RouterFunction<ServerResponse> gatewayRoutes() {
        return route("user-service-docs")
                .route(path("/api/users/v3/api-docs"), http())
                .before(uri(userServiceUrl))
                .filter(addRequestHeader("X-Forwarded-Host", "localhost:8080"))
                .filter(addRequestHeader("X-Forwarded-Port", "8080"))
                .filter(addRequestHeader("X-Forwarded-Proto", "http"))
                .filter(setPath("/v3/api-docs"))
                .build()
            .and(route("user-service")
                .route(path("/api/users/**"), http())
                .before(uri(userServiceUrl))
                .filter(addRequestHeader("X-Forwarded-Host", "localhost:8080"))
                .filter(addRequestHeader("X-Forwarded-Port", "8080"))
                .filter(addRequestHeader("X-Forwarded-Proto", "http"))
                .build())
            .and(route("product-service-docs")
                .route(path("/api/products/v3/api-docs"), http())
                .before(uri(productServiceUrl))
                .filter(addRequestHeader("X-Forwarded-Host", "localhost:8080"))
                .filter(addRequestHeader("X-Forwarded-Port", "8080"))
                .filter(addRequestHeader("X-Forwarded-Proto", "http"))
                .filter(setPath("/v3/api-docs"))
                .build())
            .and(route("product-service")
                .route(path("/api/v1/products/**"), http())
                .before(uri(productServiceUrl))
                .filter(addRequestHeader("X-Forwarded-Host", "localhost:8080"))
                .filter(addRequestHeader("X-Forwarded-Port", "8080"))
                .filter(addRequestHeader("X-Forwarded-Proto", "http"))
                .build())
            .and(route("cart-service-docs")
                .route(path("/api/carts/v3/api-docs"), http())
                .before(uri(cartServiceUrl))
                .filter(addRequestHeader("X-Forwarded-Host", "localhost:8080"))
                .filter(addRequestHeader("X-Forwarded-Port", "8080"))
                .filter(addRequestHeader("X-Forwarded-Proto", "http"))
                .filter(setPath("/v3/api-docs"))
                .build())
            .and(route("cart-service")
                .route(path("/api/v1/carts/**"), http())
                .before(uri(cartServiceUrl))
                .filter(addRequestHeader("X-Forwarded-Host", "localhost:8080"))
                .filter(addRequestHeader("X-Forwarded-Port", "8080"))
                .filter(addRequestHeader("X-Forwarded-Proto", "http"))
                .build())
            .and(route("order-service-docs")
                .route(path("/api/orders/v3/api-docs"), http())
                .before(uri(orderServiceUrl))
                .filter(addRequestHeader("X-Forwarded-Host", "localhost:8080"))
                .filter(addRequestHeader("X-Forwarded-Port", "8080"))
                .filter(addRequestHeader("X-Forwarded-Proto", "http"))
                .filter(setPath("/v3/api-docs"))
                .build())
            .and(route("order-service")
                .route(path("/api/v1/orders/**"), http())
                .before(uri(orderServiceUrl))
                .filter(addRequestHeader("X-Forwarded-Host", "localhost:8080"))
                .filter(addRequestHeader("X-Forwarded-Port", "8080"))
                .filter(addRequestHeader("X-Forwarded-Proto", "http"))
                .build())
            .and(route("payment-service-docs")
                .route(path("/api/payments/v3/api-docs"), http())
                .before(uri(paymentServiceUrl))
                .filter(addRequestHeader("X-Forwarded-Host", "localhost:8080"))
                .filter(addRequestHeader("X-Forwarded-Port", "8080"))
                .filter(addRequestHeader("X-Forwarded-Proto", "http"))
                .filter(setPath("/v3/api-docs"))
                .build())
            .and(route("payment-service")
                .route(path("/api/v1/payments/**"), http())
                .before(uri(paymentServiceUrl))
                .filter(addRequestHeader("X-Forwarded-Host", "localhost:8080"))
                .filter(addRequestHeader("X-Forwarded-Port", "8080"))
                .filter(addRequestHeader("X-Forwarded-Proto", "http"))
                .build())
            .and(route("notification-service-docs")
                .route(path("/api/notifications/v3/api-docs"), http())
                .before(uri(notificationServiceUrl))
                .filter(addRequestHeader("X-Forwarded-Host", "localhost:8080"))
                .filter(addRequestHeader("X-Forwarded-Port", "8080"))
                .filter(addRequestHeader("X-Forwarded-Proto", "http"))
                .filter(setPath("/v3/api-docs"))
                .build())
            .and(route("notification-service")
                .route(path("/api/v1/notifications/**"), http())
                .before(uri(notificationServiceUrl))
                .filter(addRequestHeader("X-Forwarded-Host", "localhost:8080"))
                .filter(addRequestHeader("X-Forwarded-Port", "8080"))
                .filter(addRequestHeader("X-Forwarded-Proto", "http"))
                .build());
    }
}
