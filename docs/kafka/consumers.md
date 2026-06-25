# Consumers

Consumers use the `@KafkaListener` annotation.
Example: `@KafkaListener(topics = "order-created", groupId = "ecommerce-group")`

Messages are automatically deserialized from JSON to Java POJOs via Jackson.
