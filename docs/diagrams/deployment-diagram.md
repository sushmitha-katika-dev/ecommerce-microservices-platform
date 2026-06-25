# Deployment Diagram

```mermaid
graph TD
    Client --> API_Gateway
    API_Gateway --> User_Service
    API_Gateway --> Product_Service
    API_Gateway --> Order_Service
    Order_Service --> Kafka
    Kafka --> Payment_Service
    Kafka --> Notification_Service
```
