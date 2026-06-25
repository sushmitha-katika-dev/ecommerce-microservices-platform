# System Architecture

The platform uses a microservices architecture to ensure high availability, horizontal scalability, and loose coupling.

## Overview
1. **API Gateway**: The single entry point for all client requests. Handles routing and JWT token validation.
2. **Microservices**: Independent business domains (User, Product, Cart, Order, Payment, Notification).
3. **Event Bus**: Apache Kafka facilitates asynchronous communication between services (e.g., Order Created -> Payment Processed -> Notification Sent).
4. **Database-per-Service**: Each service manages its own database schema to prevent tightly coupled data dependencies.

## Communication
- **Synchronous**: REST APIs via the API Gateway.
- **Asynchronous**: Kafka Topics for cross-service events.
