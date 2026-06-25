# Database Design

Pattern: **Database-per-Service**

Each microservice has its own isolated database schema. Microservices cannot directly query another service's database. Data that needs to be shared is replicated or transmitted via Kafka events.

- `user-service`: Manages `users`, `roles`, `user_roles`, `refresh_tokens`.
- `product-service`: Manages `products`, `categories`.
- `cart-service`: Manages `carts`, `cart_items`.
- `order-service`: Manages `orders`, `order_items`.
- `payment-service`: Manages `payments`.
- `notification-service`: Manages `notification_logs`.\n