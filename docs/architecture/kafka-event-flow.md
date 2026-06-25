# Kafka Event Flow

The platform relies heavily on event-driven architecture using **Apache Kafka**.

## Core Flow (Order Placement)
1. **Cart Checkout**: `cart-service` publishes `cart-checkout`.
2. **Order Creation**: `order-service` consumes checkout, creates an order, and publishes `order-created`.
3. **Inventory Reservation**: `product-service` consumes `order-created` and reduces stock.
4. **Payment Processing**: `payment-service` consumes `order-created` and attempts to process payment. Publishes `payment-completed` or `payment-failed`.
5. **Notification**: `notification-service` consumes `order-created` (to send order confirmation) and `payment-completed` (to send payment receipt).
