# Error Handling Flow

The platform centralizes error handling to ensure consistent API response structures using `@ControllerAdvice`.

```mermaid
graph TD
    Request[Incoming Request] --> Controller
    Controller --> Service
    
    Service -->|Throws Exception| Error[Exception Thrown]
    
    Error --> Handler(@RestControllerAdvice GlobalExceptionHandler)
    
    Handler --> C1{Exception Type?}
    C1 -->|MethodArgumentNotValidException| V[Validation Error Response 400]
    C1 -->|ResourceNotFoundException| NF[Not Found Response 404]
    C1 -->|InvalidCredentialsException| Auth[Unauthorized Response 401]
    C1 -->|Exception| Sys[Internal Server Error 500]
    
    V --> Output[Formatted JSON Error Response]
    NF --> Output
    Auth --> Output
    Sys --> Output
    Output --> Client
```\n