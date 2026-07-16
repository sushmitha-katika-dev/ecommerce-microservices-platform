# High-Level System Architecture

This document illustrates the macro-level architecture of the E-Commerce platform. It follows an API Gateway pattern with asynchronous event streaming via Kafka.

## System Diagram

<img src="../diagrams/architecture.svg" alt="High-Level System Architecture" width="100%">

```mermaid
graph TD
    %% Entities
    Client[React SPA / Web Client]
    Gateway[API Gateway\nSpring Cloud Gateway]
    
    %% Services
    UserSvc[User Service]
    ProdSvc[Product Service]
    CartSvc[Cart Service]
    OrderSvc[Order Service]
    PaySvc[Payment Service]
    NotifSvc[Notification Service]
    
    %% Infrastructure
    Kafka((Apache Kafka\nEvent Bus))
    
    %% Databases
    UserDB[(User DB)]
    ProdDB[(Product DB)]
    CartDB[(Cart DB)]
    OrderDB[(Order DB)]
    PayDB[(Payment DB)]
    NotifDB[(Notification DB)]

    %% Flow
    Client -->|HTTP/REST| Gateway
    Gateway -->|/api/users| UserSvc
    Gateway -->|/api/products| ProdSvc
    Gateway -->|/api/carts| CartSvc
    Gateway -->|/api/orders| OrderSvc
    Gateway -->|/api/payments| PaySvc
    Gateway -->|/api/notifications| NotifSvc
    
    %% DB Connections
    UserSvc -.-> UserDB
    ProdSvc -.-> ProdDB
    CartSvc -.-> CartDB
    OrderSvc -.-> OrderDB
    PaySvc -.-> PayDB
    NotifSvc -.-> NotifDB
    
    %% Kafka Connections
    OrderSvc -->|Publish| Kafka
    PaySvc -->|Publish| Kafka
    Kafka -->|Consume| PaySvc
    Kafka -->|Consume| NotifSvc
    Kafka -->|Consume| ProdSvc
```

### Explanatory Notes
- **React → API Gateway**: The client interacts exclusively with the API Gateway, shielding internal service complexities.
- **API Gateway → Microservices**: Request routing, rate-limiting, and central JWT verification.
- **Kafka**: Used for decoupled, eventual consistency across services.
- **Databases**: Strict database-per-service isolation pattern using MySQL.
