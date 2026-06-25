# Authentication API (User Service)

**Base Path**: `/api/users/auth`

### `POST /register`
Registers a new user.
- **Body**: `{ "email": "", "password": "", "firstName": "", "lastName": "" }`

### `POST /login`
Authenticates a user and returns a JWT.
- **Body**: `{ "email": "", "password": "" }`
- **Response**: `{ "token": "...", "user": { ... } }`
