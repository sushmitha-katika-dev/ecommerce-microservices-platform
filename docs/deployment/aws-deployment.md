# AWS Deployment

Recommended Architecture for AWS:
- **EKS (Elastic Kubernetes Service)**: Host the microservices.
- **RDS**: Managed databases for each service.
- **MSK (Managed Streaming for Apache Kafka)**: Managed Kafka cluster.
- **ALB (Application Load Balancer)**: Ingress routing to API Gateway.\n