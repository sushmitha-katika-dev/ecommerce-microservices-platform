# Microservices Overview

| Service | Port | Database | Responsibility |
|---------|------|----------|----------------|
| **api-gateway** | 8080 | N/A | Request routing, rate limiting, and JWT validation. |
| **user-service** | 8081 | `user_db` | Authentication, authorization, and user profile management. |
| **product-service** | 8082 | `product_db` | Product catalog, categories, and inventory management. |
| **cart-service** | 8083 | `cart_db` | Managing user shopping carts and cart items. |
| **order-service** | 8084 | `order_db` | Order placement, history, and checkout aggregation. |
| **payment-service** | 8085 | `payment_db` | Payment processing and transaction history. |
| **notification-service** | 8086 | `notification_db` | Email and SMS notifications for user actions (e.g., order confirmations). |\n