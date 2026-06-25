# Authentication Flow

1. User sends credentials to `POST /api/users/auth/login`.
2. `user-service` verifies credentials and generates a JWT token.
3. User includes the JWT in the `Authorization: Bearer <token>` header for subsequent requests.
4. `api-gateway` intercepts the request, validates the JWT signature and expiration.
5. If valid, the request is routed to the destination service. If invalid, a `401 Unauthorized` is returned.
