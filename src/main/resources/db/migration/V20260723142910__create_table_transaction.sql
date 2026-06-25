CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE transaction (
    transaction_id VARCHAR(40) PRIMARY KEY,
    card VARCHAR(20) NOT NULL,
    value DECIMAL(10, 2) NOT NULL,
    date TIMESTAMP NOT NULL,
    establishment VARCHAR(100),
    nsu VARCHAR(20),
    authorization_code VARCHAR(20),
    status VARCHAR(20) NOT NULL,
    payment_method_type VARCHAR(20) NOT NULL,
    installments INT DEFAULT 1
);