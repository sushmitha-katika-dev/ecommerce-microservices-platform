# Security Architecture

Security is enforced at the perimeter (Gateway) and at the method level (Microservice).

```mermaid
graph TD
    Client -->|Authorization: Bearer xyz| Gateway
    
    subgraph Spring Cloud Gateway
        Filter[JwtAuthenticationFilter]
        Filter -->|Secret Key Verification| Validates[Validates Token]
    end
    
    subgraph Microservice
        Controller[Protected API]
        AOP[@PreAuthorize annotations]
    end
    
    Validates -->|Token Valid| Controller
    Controller --> AOP
    AOP -->|User Role Matches?| Logic[Execute Business Logic]
    AOP -->|Role Mismatch| 403[HTTP 403 Forbidden]
```
