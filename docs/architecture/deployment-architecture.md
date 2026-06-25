# Deployment Architecture

The platform is designed to be containerized using **Docker** and orchestrated via **Kubernetes** or **Docker Compose** for local environments.

- **Containers**: Each Spring Boot application is containerized alongside sidecar services like MySQL and Kafka.
- **Network**: Services communicate on an internal Docker network. Only the API Gateway exposes port `8080` to the host machine.
