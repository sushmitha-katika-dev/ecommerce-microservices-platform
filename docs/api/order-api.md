# Order API

**Base Path**: `/api/v1/orders`

### `POST /`
Places a new order directly.
- **Body**: `{ "userId": "uuid", "shippingAddress": "str", "items": [...] }`

### `GET /user/{userId}`
Retrieves the order history for a specific user.

### `GET /{orderId}`
Retrieves a specific order by ID.\n