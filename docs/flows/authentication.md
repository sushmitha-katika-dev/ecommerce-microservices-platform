# Authentication Flow

This sequence illustrates how a user authenticates to receive a JWT and subsequently accesses protected APIs.

```mermaid
sequenceDiagram
    autonumber
    participant Client
    participant Gateway as API Gateway
    participant UserSvc as User Service
    participant ProtectedSvc as Protected API

    %% Login Process
    Client->>Gateway: POST /api/users/auth/login (email, pass)
    Gateway->>UserSvc: Forward Request
    UserSvc->>UserSvc: Validate Credentials
    UserSvc->>UserSvc: Generate JWT Secret Token
    UserSvc-->>Gateway: HTTP 200 + { token, userProfile }
    Gateway-->>Client: HTTP 200 + { token, userProfile }

    %% Protected Access Process
    Client->>Gateway: GET /api/v1/orders (Header: Bearer <token>)
    Gateway->>Gateway: JwtAuthenticationFilter Validates Token
    Gateway->>ProtectedSvc: Forward Request
    ProtectedSvc->>ProtectedSvc: Process Business Logic
    ProtectedSvc-->>Gateway: HTTP 200 (Data)
    Gateway-->>Client: HTTP 200 (Data)
```\n