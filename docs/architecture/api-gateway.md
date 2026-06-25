# API Gateway Architecture

The API Gateway acts as the single entry point and enforces security across all inbound traffic.

## Gateway Flow

```mermaid
graph TD
    A[Client Request] --> B[API Gateway]
    
    subgraph Spring Cloud Gateway
        B --> C{Route Matching}
        C -->|Match Found| D[JwtAuthenticationFilter]
        D --> E{Token Valid?}
        E -->|No| F[401 Unauthorized]
        E -->|Yes| G[Add Custom Headers]
    end
    
    G --> H[Downstream Microservice]
    H --> I[Service Processing]
    I --> J[Response]
    J --> K[API Gateway]
    K --> L[Client Response]
```

### Flow Details
1. **Client Request**: An incoming HTTP request hits the Gateway.
2. **Route Resolution**: `application.yml` predicates determine the target service based on the path (e.g., `/api/products/**`).
3. **JWT Validation**: The custom `JwtAuthenticationFilter` intercepts the request and verifies the signature of the `Authorization` header.
4. **Request Forwarding**: Valid requests are passed to the downstream service.
5. **Response Flow**: The response is proxied back through the gateway to the client.
