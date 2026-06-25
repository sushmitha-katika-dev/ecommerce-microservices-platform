# Order Processing Flow

The core transactional flow of the platform relies on Kafka for asynchronous choreographic orchestration.

```mermaid
sequenceDiagram
    autonumber
    actor Customer
    participant CartSvc as Cart Service
    participant OrderSvc as Order Service
    participant Kafka as Kafka Broker
    participant ProdSvc as Product Service
    participant PaySvc as Payment Service
    participant NotifSvc as Notification Service

    Customer->>CartSvc: POST /api/v1/carts/checkout
    CartSvc->>OrderSvc: (Internal or Client driven Place Order)
    OrderSvc->>OrderSvc: Save Order (Status: PENDING)
    OrderSvc->>Kafka: Publish `order-created` Event
    
    %% Parallel processing by consumers
    par Inventory Management
        Kafka-->>ProdSvc: Consume `order-created`
        ProdSvc->>ProdSvc: Reserve Inventory
    and Payment Processing
        Kafka-->>PaySvc: Consume `order-created`
        PaySvc->>PaySvc: Charge Credit Card
        PaySvc->>Kafka: Publish `payment-completed` Event
    and Order Notification
        Kafka-->>NotifSvc: Consume `order-created`
        NotifSvc->>NotifSvc: Send "Order Received" Email
    end
    
    Kafka-->>NotifSvc: Consume `payment-completed`
    NotifSvc->>NotifSvc: Send "Payment Receipt" Email
```\n