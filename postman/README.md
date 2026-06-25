# E-Commerce Microservices Platform - Postman Integration

This directory contains the complete Postman Integration for the E-Commerce Microservices Platform, including authentication chains, standard workflows, and negative testing.

## Artifacts Generated

1. **`postman_environment.json`**: The Local Environment configuration containing URLs for all microservices, API Gateway, and the JWT token variable.
2. **`postman_collection.json`**: The Postman Collection containing all requests logically grouped into folders (Authentication, Product, Cart, Order, Payment, Notification, Negative Test Cases).

## Features Included

- **Automatic Authorization**: The entire collection is configured to inherit a Bearer Token. The `jwtToken` environment variable is automatically populated when you successfully run the `Login` request.
- **Microservice Routing**: Each endpoint is configured using its respective `{{serviceUrl}}` environment variable for direct testing.
- **Example Payloads**: Fully configured JSON bodies for creating products, adding to cart, and placing orders.
- **Negative Testing**: A dedicated folder for testing missing tokens, invalid payloads, and bad credentials.

## How to Run the Collection

### Prerequisites
Make sure your local microservices (and API Gateway) are running on their default ports.

### 1. Import the Artifacts into Postman
1. Open Postman.
2. Click **Import** (top left).
3. Select both `postman_collection.json` and `postman_environment.json` files and import them.

### 2. Select the Environment
1. In the top right corner of Postman, click the Environment dropdown.
2. Select **E-Commerce Local Env**.
3. (Optional) Click the "eye" icon to verify that all the `{{serviceUrl}}` variables are correctly populated with `http://localhost:808x`.

### 3. Run the Authentication Flow
1. Open the **Authentication** folder.
2. Run **Register User** to create a test account.
3. Run **Login**. 
   - *Note: A Postman test script is attached to this request. Upon a successful 200 OK response, the script extracts the `token` from the response body and saves it directly to your Environment Variables.*

### 4. Execute the Requests
Because the `jwtToken` is saved automatically, you can now run any request in the Product, Cart, Order, or Payment folders without manually copying the token! 
- Go to **Product > Create Product** and hit Send.
- Go to **Cart > Add to Cart** and hit Send.
- Go to **Order > Place Order** and hit Send.

### 5. Running the Complete Test Suite
You can also run the entire collection automatically using Postman's Collection Runner:
1. Click on the **E-Commerce Microservices Platform** collection name.
2. Click the **Run** button.
3. Ensure the environment is set to **E-Commerce Local Env**.
4. Click **Run E-Commerce Microservices Platform**.
