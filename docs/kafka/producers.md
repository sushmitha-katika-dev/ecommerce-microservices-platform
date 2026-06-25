# Producers

Producers utilize `KafkaTemplate<String, Object>` to send serialized JSON payloads to topics.
Each producer is wrapped in a publisher component, e.g., `OrderEventPublisher.java`.\n