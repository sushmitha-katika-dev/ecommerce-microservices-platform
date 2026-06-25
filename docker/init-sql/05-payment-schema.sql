USE ecommerce_payment_db;

CREATE TABLE payments (
    id VARCHAR(36) PRIMARY KEY,
    order_id VARCHAR(36) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    provider VARCHAR(50),
    status VARCHAR(50) NOT NULL,
    transaction_id VARCHAR(255) UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version INT DEFAULT 0
);

CREATE INDEX idx_pay_order ON payments(order_id);
CREATE INDEX idx_pay_tx ON payments(transaction_id);
