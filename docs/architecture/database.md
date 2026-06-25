# Database Architecture

The platform strictly adheres to the **Database-per-Service** pattern.

```mermaid
graph TD
    User[User Service] --> U_DB[(user_db)]
    Prod[Product Service] --> P_DB[(product_db)]
    Cart[Cart Service] --> C_DB[(cart_db)]
    Order[Order Service] --> O_DB[(order_db)]
    Pay[Payment Service] --> Pay_DB[(payment_db)]
    Notif[Notification Service] --> N_DB[(notification_db)]
```

## Why Database-per-Service?
1. **Loose Coupling**: A schema change in the `user_db` has zero impact on the `order-service`.
2. **Independent Scaling**: If `order_db` faces heavy I/O, it can be scaled or optimized independently from `notification_db`.
3. **Technology Agnostic**: While all current services use MySQL, future services could easily adopt NoSQL (e.g., MongoDB for Cart Service) without affecting the ecosystem.\n