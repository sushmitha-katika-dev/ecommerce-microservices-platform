# Kafka Event Architecture

Apache Kafka acts as the central nervous system of the microservices platform, enabling highly decoupled, asynchronous workflows.

## Topics, Producers, and Consumers

```mermaid
graph LR
    %% Producers
    P_Order(Order Event Publisher)
    P_Pay(Payment Event Publisher)

    %% Topics
    T_OrderCreated[[Topic: order-created]]
    T_PayCompleted[[Topic: payment-completed]]

    %% Consumers
    C_Prod(Product Event Consumer)
    C_Pay(Payment Event Consumer)
    C_NotifOrder(Notification Event Consumer)
    C_NotifPay(Notification Event Consumer)

    %% Flow
    P_Order -->|Publishes JSON| T_OrderCreated
    P_Pay -->|Publishes JSON| T_PayCompleted
    
    T_OrderCreated -->|Consumes| C_Prod
    T_OrderCreated -->|Consumes| C_Pay
    T_OrderCreated -->|Consumes| C_NotifOrder
    
    T_PayCompleted -->|Consumes| C_NotifPay
    
    %% Styling
    style T_OrderCreated fill:#f96,stroke:#333,stroke-width:2px
    style T_PayCompleted fill:#f96,stroke:#333,stroke-width:2px
```

### Event Contracts
All payloads are serialized as JSON. Strict POJO representation ensures compatibility between producers and consumers (e.g., `OrderCreatedEvent` payload).\n