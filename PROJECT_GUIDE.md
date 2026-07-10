# E-Commerce Microservices Platform

Welcome to the **E-Commerce Microservices Platform**! This enterprise-grade repository showcases a highly decoupled, scalable, and event-driven backend architecture built with **Java 17** and **Spring Boot 3**. 

This system is designed as a portfolio piece to demonstrate mastery over modern distributed system patterns, including API Gateways, Event-Driven Choreography via Kafka, centralized JWT Security, and the Database-per-Service pattern.

---

## 🏛 Architecture Overview

The system consists of six distinct microservices hidden behind a single Spring Cloud API Gateway. 

![Swagger Gateway Aggregation](docs/screenshots/system/04-swagger-gateway-aggregation.png)

### Microservices Overview

1. **User Service** (Port `8081`)
   - **Responsibility**: Manages user registration, profiles, authentication, and generates JWTs using a hardcoded secure secret.
   - **Data Store**: MySQL (`ecommerce_user_db`)
2. **Product Service** (Port `8082`)
   - **Responsibility**: Manages the product catalog, categories, and inventory.
   - **Data Store**: MySQL (`ecommerce_product_db`) + **Redis** (for high-speed catalog caching)
3. **Cart Service** (Port `8083`)
   - **Responsibility**: Manages ephemeral shopping cart sessions. Users add/remove items to a cart before initiating checkout.
   - **Data Store**: MySQL (`ecommerce_cart_db`)
4. **Order Service** (Port `8084`)
   - **Responsibility**: Listens to Kafka checkout events and creates immutable order records.
   - **Data Store**: MySQL (`ecommerce_order_db`)
5. **Payment Service** (Port `8085`)
   - **Responsibility**: Processes mocked payments based on the total order amount.
   - **Data Store**: MySQL (`ecommerce_payment_db`)
6. **Notification Service** (Port `8086`)
   - **Responsibility**: Listens to Kafka topics to send simulated email/SMS alerts to users upon order creation and payment success.
   - **Data Store**: None (Stateless worker)

---

## 🛠 Technology Stack

- **Backend Framework**: Java 17, Spring Boot 3.x, Spring Cloud Gateway
- **Data Persistence**: MySQL, Spring Data JPA, Hibernate
- **Event Streaming**: Apache Kafka, Zookeeper
- **Caching**: Redis, Spring Data Redis
- **Security**: Spring Security, stateless JWT (JSON Web Tokens)
- **Observability**: Micrometer Tracing, Zipkin UI
- **Containerization**: Docker, Docker Compose
- **Build Tool**: Maven, GitHub Actions (CI/CD)

---

## 🔐 Core Architectural Patterns

### 1. API Gateway & Routing
All client requests route through the **API Gateway** on port `8080`.
- **Prefix Routing**: Requests prefixed with `/api/v1/` are seamlessly forwarded to the respective downstream microservices without exposing their internal ports.
- **Header Injection**: Employs `X-Forwarded-*` headers so internal microservices can accurately generate callback URLs, enabling Swagger UI to function behind the proxy inside Docker.

### 2. JWT Authentication Flow
1. A user logs in via the Gateway `POST /api/users/auth/login`.
2. The User Service validates credentials and returns a signed **JWT**.
3. For subsequent requests, the client includes the JWT in the `Authorization: Bearer <token>` header.
4. The **API Gateway** intercepts the request, validates the cryptographic signature of the token using the shared secret, extracts the `ROLE_CUSTOMER` authorities, and populates the `SecurityContext`.
5. The request is forwarded downstream. Downstream services do *not* need to re-verify the token.

### 3. Kafka Event Flow (Choreography)
The platform uses **Event-Driven Choreography** to orchestrate distributed transactions:
1. **Checkout**: The user hits `POST /api/v1/carts/{id}/checkout`. Cart Service validates inventory and produces an `order-checkout` event.
2. **Order Creation**: Order Service consumes `order-checkout`, creates an `Order`, and produces an `order-created` event.
3. **Payment**: Payment Service consumes `order-created`, processes a mock payment, and produces a `payment-completed` event.
4. **Notifications**: Notification Service concurrently consumes both `order-created` and `payment-completed` to dispatch alerts to the user.

### 4. Database-per-Service Architecture
Every stateful microservice is physically isolated with its own dedicated MySQL database. There are no shared tables and no cross-database foreign keys. Services communicate strictly via REST APIs (synchronous) or Kafka (asynchronous), ensuring true domain decoupling.

---

## 📁 Project Structure

```text
ecommerce-microservices-platform/
├── api-gateway/          # Edge routing and security filtering
├── cart-service/         # Ephemeral shopping cart sessions
├── docker/               # Database initialization scripts
├── docs/                 # Detailed architectural diagrams and screenshots
├── frontend/             # React SPA (Client)
├── notification-service/ # Kafka-triggered email/SMS alerts
├── order-service/        # Order creation and lifecycle
├── payment-service/      # Transaction processing
├── postman/              # End-to-End integration testing suite
├── product-service/      # Catalog and inventory management
└── user-service/         # User profile and Auth/JWT management
```

---

## ⚙️ Prerequisites & Installation Guide

### Prerequisites
- JDK 17+
- Maven 3.8+
- Docker Desktop
- Postman (for testing)

### 1. Start Infrastructure (Databases, Kafka, Redis, Zipkin)
Navigate to the root directory and start the core dependencies via Docker Compose.
```bash
docker-compose up -d
```
![Docker Compose Healthy](docs/screenshots/system/02-docker-compose-healthy.png)

### 2. Build Microservices
The project includes a parent POM for convenient 1-click building.
```bash
mvn clean install -DskipTests
```

### 3. Run the Microservices
You can run each Spring Boot application via your IDE or using the Maven wrapper.
```bash
mvn spring-boot:run -pl api-gateway
mvn spring-boot:run -pl user-service
# Repeat for other services
```

---

## 🐳 Docker Setup & Commands

The entire platform is fully containerized. You do not need Java installed on your host machine to run the production build.

**Start the entire stack (Infrastructure + Microservices):**
```bash
docker-compose up -d --build
```
![Docker Desktop Running](docs/screenshots/system/01-docker-desktop-running.png)

**View aggregated logs:**
```bash
docker-compose logs -f
```

**Tear down the environment (and wipe databases):**
```bash
docker-compose down -v
```

---

## 📖 Swagger / OpenAPI Guide

We have implemented an **Aggregated Swagger UI** directly inside the API Gateway.
1. Start the platform.
2. Navigate to [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).
3. Use the **Select a definition** dropdown in the top right corner to instantly switch between the API documentation for any of the 6 microservices.

---

## 🧪 Postman Integration Guide

We provide a complete automated testing suite inside the `postman/` directory.

1. Import `postman_collection.json` and `postman_environment.json` into Postman.
2. Select the **E-Commerce Local Env** environment.
3. Run the **Authentication > Login** request. The test script will automatically extract the JWT and seed the `{{jwtToken}}` environment variable for all subsequent requests!
![User Login](docs/screenshots/system/06-user-login-jwt-token.png)

**Executing the Flow:**
Execute the collection in order. 
*Note:* The Cart Service `checkout` endpoint operates **asynchronously**. When you check out a cart, you will immediately receive a 202 Accepted. The Order, Payment, and Notification services will process the rest of the flow in the background via Kafka!

### Comprehensive Postman Test Results

Below are the successful execution results of all APIs across the microservices, proving end-to-end functionality.

#### User Service
![Register User](docs/screenshots/postman/user-service/register-user.png)
![Login Customer](docs/screenshots/postman/user-service/login-customer.png)

#### Product Service
![Create Category](docs/screenshots/postman/product-service/create-category.png)
![Create Sub-Category](docs/screenshots/postman/product-service/create-sub-category.png)
![Get All Categories](docs/screenshots/postman/product-service/get-all-categories.png)
![Get Category By ID](docs/screenshots/postman/product-service/get-category-by-id.png)
![Create Products](docs/screenshots/postman/product-service/create-products.png)
![List Products](docs/screenshots/postman/product-service/list-products.png)
![Update Product](docs/screenshots/postman/product-service/update-product.png)

#### Cart Service
![Add To Cart](docs/screenshots/postman/cart-service/add-to-cart.png)
![View Cart](docs/screenshots/postman/cart-service/view-cart.png)
![Update Item Quantity](docs/screenshots/postman/cart-service/update-item-quantity.png)
![Remove Cart Item](docs/screenshots/postman/cart-service/remove-cart-item.png)
![Checkout Order](docs/screenshots/postman/cart-service/checkout-order.png)
![Clear Cart](docs/screenshots/postman/cart-service/clear-cart.png)

#### Order Service (Async via Kafka)
![Get All Orders](docs/screenshots/postman/order-service/get-all-orders.png)
![Place Order](docs/screenshots/postman/order-service/place-order.png)

#### Payment Service (Async via Kafka)
![Get Payment By ID](docs/screenshots/postman/payment-service/get-payment-by-id.png)
![View Payment History](docs/screenshots/postman/payment-service/view-payment-history.png)

#### Notification Service (Async via Kafka)
![Get Notification By ID](docs/screenshots/postman/notification-service/get-notification-by-id.png)
![Verify Notification](docs/screenshots/postman/notification-service/verify-notification.png)

---

## 🔍 Runtime Verification & Results

To verify the system is operating perfectly in an event-driven manner:
1. Complete a cart checkout in Postman.
2. Connect to the Order Service database:
   ```bash
   docker exec -it mysql mysql -uroot -proot -e "USE ecommerce_order_db; SELECT * FROM orders;"
   ```
3. Connect to the Payment Service database to verify the payment was processed:
   ```bash
   docker exec -it mysql mysql -uroot -proot -e "USE ecommerce_payment_db; SELECT * FROM payments;"
   ```
4. Check the Zipkin UI at `http://localhost:9411` to view distributed traces of your HTTP requests navigating through the gateway and down to the services!

---

## 🔮 Future Enhancements
- Migrate from Docker Compose to a local Kubernetes (Minikube) deployment cluster.
- Implement a complete frontend web application inside the `frontend/` directory to consume the APIs.
- Add GitHub Actions CI/CD to automatically push Docker images to DockerHub.

---

## 📄 License & Author
This project is licensed under the MIT License.

Designed and developed by **Sushmitha Katika**.