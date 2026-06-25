# Kafka Topics

| Topic Name | Producer | Consumers | Purpose |
|------------|----------|-----------|---------|
| `order-created` | `order-service` | `product`, `payment`, `notification` | Emitted when a new order is saved. |
| `payment-completed` | `payment-service` | `notification-service` | Emitted when a payment succeeds. |
| `payment-failed` | `payment-service` | - | Emitted when a payment fails. |
| `product-events` | `product-service` | - | Emitted when products are updated. |
| `cart-checkout` | `cart-service` | `order-service` | Emitted when cart checkout is requested. |\n