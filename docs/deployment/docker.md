# Docker Architecture

The platform relies on containerization to ensure identical execution environments across local development and production.

```mermaid
graph TD
    subgraph Docker Host
        subgraph ecommerce-network [Docker Custom Network]
            GW[api-gateway]
            U[user-service]
            P[product-service]
            C[cart-service]
            O[order-service]
            Pay[payment-service]
            N[notification-service]
            
            K[kafka-broker]
            Z[zookeeper]
            DB[mysql-db]
        end
        
        subgraph Docker Volumes
            DBData[(mysql-data)]
            KafkaData[(kafka-data)]
        end
    end

    DB --> DBData
    K --> KafkaData
    
    %% Inter-container communication
    GW -.->|http://user-service:8081| U
    U -.->|jdbc:mysql://mysql-db:3306/user_db| DB
```

### Explanatory Notes
- **Docker Network**: Services address each other using Docker container names (e.g., `mysql-db` instead of `localhost`).
- **Volumes**: Data stores (MySQL, Kafka) map to persistent Docker volumes to survive container restarts.
- **Ports**: Only the Gateway (`8080`) needs to be mapped to the host machine.
