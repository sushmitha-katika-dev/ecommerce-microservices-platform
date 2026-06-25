# Local Setup

## Prerequisites
- Java 17
- Maven 3.8+
- Docker & Docker Compose (for databases & Kafka)

## Running Locally
1. Start infrastructure: `docker-compose up -d`
2. Compile project: `mvn clean install -DskipTests`
3. Run microservices manually or via IDE dashboard.\n