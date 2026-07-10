# E-Commerce Microservices Platform

Welcome to the **E-Commerce Microservices Platform**! This repository serves as a complete, scalable backend architecture for an e-commerce store, built using modern Java and Spring Boot technologies.

## 🚀 Architecture Overview

The platform uses a robust microservices architecture comprising several core services, all communicating seamlessly:

- **API Gateway**: Central entry point and reverse proxy handling all external routing and aggregate API documentation (`spring-cloud-starter-gateway-mvc`).
- **User Service**: Manages user authentication and JWT token generation.
- **Product Service**: Handles the product catalog and inventory.
- **Cart Service**: Manages the user's shopping cart.
- **Order Service**: Coordinates order placement and history.
- **Payment Service**: Processes transactions and mock payments.
- **Notification Service**: Listens for system events (via Kafka) and dispatches notifications.

### 🛠 Tech Stack
- **Framework**: Spring Boot (v3.2.x+ / 4.1.0) & Spring Cloud (2025.1.2)
- **Database**: MySQL (relational persistence) & H2 (testing)
- **Messaging**: Apache Kafka & Zookeeper (Event-driven communication)
- **Security**: Spring Security with JWT
- **API Documentation**: OpenAPI / Swagger UI
- **Containerization**: Docker & Docker Compose

---

## 🏃 Getting Started (Local Deployment)

To run the entire platform locally, you only need Docker installed. We use Docker Compose to orchestrate all databases, messaging brokers, and Spring Boot services.

1. **Build all services and start the containers**:
   ```bash
   mvn clean package -DskipTests
   docker compose up -d --build
   ```

2. **Verify services are running**:
   Wait about 30 seconds for the databases and brokers to initialize, and the Java services to boot.
   ```bash
   docker compose ps
   ```

---

## 📚 API Documentation (Swagger UI)

We have implemented an aggregated Swagger UI at the API Gateway level. You do not need to hunt down individual service endpoints to see the documentation!

- **Access the Dashboard**: Navigate to [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- You can switch between definitions for `user-service`, `product-service`, `order-service`, etc., using the dropdown in the top right corner.

---

## 🧪 Testing the Flow

Here is a quick flow to demonstrate the system working end-to-end:

### 1. Authenticate a User
```bash
curl -X POST http://localhost:8080/api/users/auth/login \
     -H "Content-Type: application/json" \
     -d '{"email":"test@example.com", "password":"password123"}'
```
*Take note of the JWT token returned in the response.*

### 2. View Products
```bash
curl http://localhost:8080/api/products \
     -H "Authorization: Bearer <YOUR_JWT_TOKEN>"
```

### 3. Add to Cart
```bash
curl -X POST http://localhost:8080/api/carts \
     -H "Authorization: Bearer <YOUR_JWT_TOKEN>" \
     -H "Content-Type: application/json" \
     -d '{"productId": "123", "quantity": 1}'
```

---

## 🛑 Shutting Down

To tear down the environment and remove the containers, networks, and volumes:
```bash
docker compose down -v
```

## ✨ Highlights for Portfolio
* **Robust Gateway Configuration**: Custom Java-based router configurations ensure resilient reverse-proxying.
* **Event-Driven Architecture**: Uses Kafka for asynchronous tasks like sending notifications when orders are placed.
* **Centralized Documentation**: Uses `springdoc-openapi` to aggregate all API endpoints from 6 different microservices into a single pane of glass.
