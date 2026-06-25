USE ecommerce_notification_db;

CREATE TABLE IF NOT EXISTS notification_logs (
    id VARCHAR(255) PRIMARY KEY,
    order_id VARCHAR(255),
    event_type VARCHAR(255),
    message VARCHAR(255),
    created_at DATETIME(6)
);
