# Spring Security

`user-service` manages the core authentication mechanism. It implements `UserDetailsService` and uses BCrypt for password hashing. All endpoints requiring authentication are protected using `@PreAuthorize` or Security Filter Chains.
