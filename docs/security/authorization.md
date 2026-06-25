# Authorization

Authorization happens at two levels:
1. **API Gateway**: Validates the token's authenticity.
2. **Microservices**: Validate the user's roles and permissions using Spring Security context populated by the token claims.\n