# JWT Authentication

JSON Web Tokens (JWT) are used for stateless session management.
- **Generation**: `user-service` generates JWTs using a secret key.
- **Validation**: `api-gateway` validates the JWT signature and expiration for all downstream requests using a customized `JwtAuthenticationFilter`.
