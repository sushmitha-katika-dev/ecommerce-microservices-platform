# Component Diagram

This diagram maps the macro components of the infrastructure ecosystem and how they interlock.

```mermaid
C4Context
    title Component Diagram for E-Commerce Platform

    Person(customer, "Customer", "A buyer on the platform")
    
    System_Boundary(b1, "Spring Boot Microservices Ecosystem") {
        System(gateway, "API Gateway", "Spring Cloud Gateway (Routing & Auth)")
        System(user, "User Service", "Auth & Profile")
        System(product, "Product Service", "Catalog")
        System(cart, "Cart Service", "Session Management")
        System(order, "Order Service", "Order Lifecycle")
        System(payment, "Payment Service", "Transactions")
        System(notif, "Notification Service", "Emails/SMS")
    }

    SystemDb(db, "Databases", "6 Isolated MySQL Instances")
    SystemQueue(kafka, "Apache Kafka", "Event Broker")

    Rel(customer, gateway, "HTTPS / REST")
    Rel(gateway, user, "Routes")
    Rel(gateway, product, "Routes")
    Rel(gateway, cart, "Routes")
    Rel(gateway, order, "Routes")
    Rel(gateway, payment, "Routes")
    Rel(gateway, notif, "Routes")
    
    Rel(user, db, "JDBC")
    Rel(product, db, "JDBC")
    Rel(cart, db, "JDBC")
    Rel(order, db, "JDBC")
    Rel(payment, db, "JDBC")
    Rel(notif, db, "JDBC")

    Rel(order, kafka, "Publishes")
    Rel(payment, kafka, "Publishes/Consumes")
    Rel(notif, kafka, "Consumes")
    Rel(product, kafka, "Consumes")
```\n