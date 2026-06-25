# Microservice Architecture

Each service within the platform is built on Spring Boot and follows a strict internal layered architecture.

## Common Architecture Diagram

```mermaid
classDiagram
    class Controller {
        <<REST Endpoint>>
        +handleRequest()
    }
    class ServiceInterface {
        <<Interface>>
        +executeBusinessLogic()
    }
    class ServiceImpl {
        <<Implementation>>
        +executeBusinessLogic()
    }
    class Mapper {
        <<Component>>
        +toDto(Entity)
        +toEntity(Dto)
    }
    class Repository {
        <<Spring Data JPA>>
        +save(Entity)
    }
    class Entity {
        <<JPA Entity>>
        -String id
    }
    class Database {
        <<MySQL>>
    }

    Controller --> ServiceInterface : Injects
    ServiceInterface <|.. ServiceImpl : Implements
    ServiceImpl --> Repository : Injects
    ServiceImpl --> Mapper : Uses
    Mapper --> Entity : Maps
    Repository --> Database : Persists
```

## Service Responsibilities & Ownership

| Microservice | Primary Responsibility | Database Ownership | Dependencies |
|--------------|------------------------|--------------------|--------------|
| **User** | Authentication, JWT issuing, user profile management. | `user_db` | None |
| **Product** | Catalog browsing, inventory tracking. | `product_db` | Consumes `order-created` |
| **Cart** | Temporary storage of shopping session items. | `cart_db` | None |
| **Order** | Order creation, validation, and history management. | `order_db` | Publishes `order-created` |
| **Payment** | Processing financial transactions for orders. | `payment_db` | Consumes `order-created`, Publishes `payment-completed` |
| **Notification**| Asynchronous email/SMS notifications to users. | `notification_db` | Consumes `order-created`, `payment-completed` |\n