# Migrations

The platform currently relies on Spring Boot initialization scripts (`data.sql` and `schema.sql`) and Docker initialization scripts (`/docker-entrypoint-initdb.d/`).

For production environments, tools like Flyway or Liquibase should be configured in the POM to track schema versions across microservices.
