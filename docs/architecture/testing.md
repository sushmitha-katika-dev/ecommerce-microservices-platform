# Testing Architecture

The platform utilizes a comprehensive testing pyramid strategy.

```mermaid
graph TD
    subgraph End-to-End & Integration Testing
        Postman[Postman Collection] -->|HTTP Requests| Gateway[API Gateway]
        Gateway --> Services[Microservices]
        Services --> DB[Databases]
        Services --> Kafka[Apache Kafka]
    end
    
    subgraph Unit Testing
        JUnit[JUnit 5] --> Mockito[Mockito Mocks]
        JUnit --> Controllers
        JUnit --> Services_Internal[Service Implementations]
    end
    
    Postman -.->|Tests Contracts| JUnit
```

### Components
- **Postman**: Automates E2E testing mimicking real frontend client behavior.
- **JUnit & Mockito**: Isolates logic for rapid, reliable unit testing inside the CI pipeline.
