# Package Diagram

All microservices adhere to an identical internal folder and architectural package structure.

```mermaid
graph TD
    subgraph com.ecommerce.service_name
        Config[config/]
        Security[security/]
        Controller[controller/]
        DTO[dto/]
        Service[service/]
        ServiceImpl[service/impl/]
        Repository[repository/]
        Entity[entity/]
        Exception[exception/]
        Mapper[mapper/]
        Kafka[kafka/]
    end

    Controller -->|Uses| DTO
    Controller -->|Injects| Service
    Service -->|Implemented By| ServiceImpl
    ServiceImpl -->|Injects| Repository
    ServiceImpl -->|Uses| Mapper
    Mapper -->|Maps| DTO
    Mapper -->|Maps| Entity
    Repository -->|Manages| Entity
    Kafka -->|Injects| Service
```\n