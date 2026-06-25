import os
import glob

consumer_config = """    consumer:
      group-id: ${spring.application.name}-group
      auto-offset-reset: earliest
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
    properties:
      spring.json.trusted.packages: "*"
"""

# Find all application.yml
files = glob.glob('C:/spring-workspace/ecommerce-microservices-platform/*/src/main/resources/application.yml')

for file in files:
    with open(file, 'r') as f:
        content = f.read()
        
    if 'kafka:' in content and 'group-id:' not in content:
        # Insert consumer config right after bootstrap-servers
        content = content.replace('    bootstrap-servers: localhost:9092\n', f'    bootstrap-servers: localhost:9092\n{consumer_config}')
        
        with open(file, 'w') as f:
            f.write(content)
        print(f"Fixed {file}")
