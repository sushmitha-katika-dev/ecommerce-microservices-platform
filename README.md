# E-Commerce Microservices Platform

Welcome to the **E-Commerce Microservices Platform**! This enterprise-grade repository showcases a highly decoupled, scalable, and event-driven backend architecture using Java Spring Boot.



## 🚀 Features
- **Strict Microservices Architecture**: Separation of concerns with dedicated domains (User, Product, Cart, Order, Payment, Notification).
- **API Gateway**: Single entry point handling routing (with `/api/v1/` prefixing), CORS, header forwarding (for internal Docker hostnames), and centralized JWT authorization.
- **Event-Driven Choreography**: Asynchronous operations utilizing Apache Kafka (`order-created`, `payment-completed`).
- **Database-per-Service**: Complete data isolation. Each microservice governs its own MySQL instance.
- **Security**: Robust stateless session management using JWT and Spring Security RBAC.
- **High-Performance Caching**: Redis caching layer implemented in the Product Service to drastically reduce database load.
- **Fault Tolerance**: Resilience4J Circuit Breakers prevent cascading failures during inter-service communication.
- **Distributed Tracing**: Full observability using Micrometer and Zipkin to trace requests across the entire microservice web.
- **Continuous Integration**: Automated GitHub Actions CI/CD pipeline for Maven builds and testing.
- **User Profiles**: Comprehensive user registration with phone, gender, and full address support.
- **Product Taxonomy**: Hierarchical category management and integrated inventory tracking.

## 🛠️ Tech Stack
- **Backend Core**: Java 17, Spring Boot 3.x, Spring Cloud Gateway
- **Data & Messaging**: MySQL, Spring Data JPA, Hibernate, Apache Kafka
- **Caching & Resilience**: Redis, Spring Data Redis, Resilience4J Circuit Breaker
- **Observability**: Micrometer Tracing, Zipkin
- **Security**: Spring Security, JWT (JSON Web Tokens)
- **Deployment & DevOps**: Docker, Docker Compose, Maven, GitHub Actions (CI/CD)
- **Testing**: Postman Integration Suite, JUnit 5, Mockito

## 📁 Project Structure

```text
ecommerce-microservices-platform/
├── api-gateway/          # Edge routing and security filtering
├── cart-service/         # Ephemeral shopping cart sessions
├── docker/               # Database initialization and container configs
├── docs/                 # Detailed architectural diagrams and flows
├── frontend/             # React SPA (Client)
├── notification-service/ # Kafka-triggered email/SMS alerts
├── order-service/        # Order creation and lifecycle
├── payment-service/      # Transaction processing
├── postman/              # End-to-End integration testing suite
├── product-service/      # Catalog and inventory management
└── user-service/         # User profile and Auth/JWT management
```

## 📖 Deep-Dive Architecture Documentation
Our platform features an extensive `docs/` suite covering component interactions, database topologies, and sequence diagrams.
- [High-Level Architecture](docs/architecture/high-level.md)
- [Microservices Overview](docs/architecture/microservices.md)
- [Kafka Event Flow](docs/architecture/kafka.md)
- [Authentication Flow](docs/flows/authentication.md)
- [Order Processing Sequence](docs/flows/order-processing.md)
- [Database Architectures](docs/architecture/database.md)
- [Full API Reference](docs/api/endpoints.md)

## ⚙️ Installation & Local Setup

### Prerequisites
- JDK 17+
- Maven 3.8+
- Docker & Docker Compose

### 1. Start Infrastructure (Databases & Kafka)
```bash
docker-compose up -d
```

### 2. Build Microservices
The project includes a parent POM for convenient 1-click building.
```bash
mvn clean install -DskipTests
```

### 3. Run Services
You can run each Spring Boot application via your IDE or using the Maven wrapper:
```bash
mvn spring-boot:run -pl api-gateway
mvn spring-boot:run -pl user-service
# Repeat for other services
```

## 🐳 Docker Setup
The entire platform, including all microservices, the API Gateway, and the infrastructure (MySQL, Kafka, Zookeeper, Zipkin, Redis, and ELK stack), is fully containerized.
1. Start the entire environment with a single command:
```bash
docker-compose up -d --build
```
This will automatically build the images for all services and launch them alongside the required infrastructure.

## 🧪 Testing with Postman
We provide a complete automated testing suite inside the `postman/` directory.
1. Import `postman_collection.json` and `postman_environment.json` into Postman.
2. Select the **E-Commerce Local Env** environment.
3. Run the **Authentication > Login** request to automatically seed the `{{jwtToken}}` variable.
4. Execute the collection to simulate end-to-end user flows!
   - *Note:* The Cart Service `checkout` endpoint acts asynchronously via Kafka, whereas the Order Service `place-order` endpoint acts synchronously.
Read the full [Postman Testing Guide](postman/README.md).

## 🔮 Future Improvements
- Migrate from Docker Compose to a local Kubernetes (Minikube) deployment cluster.
- Integrate a frontend framework (e.g., React or Angular) to consume the APIs.

## 📄 License
This project is licensed under the MIT Lic.

## ✍️ Author
Designed and developed by **Sushmitha Katika**.