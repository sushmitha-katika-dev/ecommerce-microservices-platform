# API Gateway

The API Gateway is built using **Spring Cloud Gateway**.

## Responsibilities
- **Routing**: Forwarding `/api/users/**` to `user-service`, `/api/products/**` to `product-service`, etc.
- **Authentication Filter**: Verifies JWT tokens on incoming requests before routing them to downstream microservices. Requests to public endpoints like `/api/users/auth/**` are permitted without tokens.
- **CORS**: Handles Cross-Origin Resource Sharing settings centrally.\n