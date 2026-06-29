USE ecommerce_user_db;

CREATE TABLE users (
    id VARCHAR(36) PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    phone_number VARCHAR(20),
    gender VARCHAR(20),
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version INT DEFAULT 0
);

CREATE INDEX idx_users_email ON users(email);

CREATE TABLE roles (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version INT DEFAULT 0
);

CREATE INDEX idx_roles_name ON roles(name);

CREATE TABLE user_roles (
    user_id VARCHAR(36),
    role_id VARCHAR(36),
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE addresses (
    id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    type VARCHAR(50) NOT NULL,
    street VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100),
    zip_code VARCHAR(20),
    country VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version INT DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE INDEX idx_addr_user ON addresses(user_id);

CREATE TABLE refresh_tokens (
    id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    token VARCHAR(255) NOT NULL UNIQUE,
    expiry_date TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE INDEX idx_refresh_token_user ON refresh_tokens(user_id);

-- Seed Data
INSERT INTO roles (id, name) VALUES ('role-admin-id', 'ADMIN');
INSERT INTO roles (id, name) VALUES ('role-customer-id', 'CUSTOMER');

-- Password is 'admin123' (bcrypt encoded)
INSERT INTO users (id, email, password_hash, first_name, last_name, status, phone_number, gender) 
VALUES ('user-admin-id', 'admin@ecommerce.com', '$2a$10$7O4Yl1b5Tf1O1E.B2A5t5.2n8C4z4A6K.D7L8M9N0P1Q2R3S4T5U6', 'Super', 'Admin', 'ACTIVE', '1234567890', 'MALE');

INSERT INTO user_roles (user_id, role_id) VALUES ('user-admin-id', 'role-admin-id');
