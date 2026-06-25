# Cart API

**Base Path**: `/api/v1/carts`

### `GET /{sessionId}`
Retrieves the cart for a session.

### `POST /{sessionId}/items`
Adds an item to the cart.
- **Body**: `{ "productId": "uuid", "quantity": 1 }`

### `DELETE /{sessionId}/items/{productId}`
Removes an item from the cart.

### `POST /{sessionId}/checkout`
Triggers the checkout process for a cart.
