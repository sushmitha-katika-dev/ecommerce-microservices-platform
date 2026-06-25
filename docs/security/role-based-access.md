# Role Based Access Control (RBAC)

Users can be assigned roles like `USER` and `ADMIN`.
Endpoints can be restricted using `@PreAuthorize("hasRole('ADMIN')")`.
