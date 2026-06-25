# Runtime Request Flow

A detailed breakdown of how a single HTTP request propagates through the platform's layers.

```mermaid
graph TD
    A[Client] -->|1. GET /api/v1/orders/123| B(API Gateway)
    B -->|2. Validate JWT Signature| C{Is Token Valid?}
    C -->|No| D[Return 401 Unauthorized]
    C -->|Yes| E[Route to Order Service]
    
    E --> F[OrderController]
    F -->|3. Call Method| G[OrderServiceImpl]
    G -->|4. Query DB| H[OrderRepository]
    H -->|5. Return Entity| G
    G -->|6. Convert to DTO| I[OrderMapper]
    I -->|7. Return DTO| G
    G -->|8. Return Response| F
    F -->|9. HTTP 200 OK| B
    B -->|10. Relay Response| A
```\n