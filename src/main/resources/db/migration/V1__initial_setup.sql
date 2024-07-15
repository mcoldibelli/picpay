CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    document VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    balance DECIMAL(19, 2) DEFAULT 0.00 NOT NULL,
    user_type VARCHAR(255) NOT NULL CHECK (user_type IN ('COMMON', 'MERCHANT'))
);

CREATE TABLE transactions (
      id SERIAL PRIMARY KEY,
      value DECIMAL(19, 2) NOT NULL,
      payer_id BIGINT NOT NULL,
      payee_id BIGINT NOT NULL,
      timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
      FOREIGN KEY (payer_id) REFERENCES users(id),
      FOREIGN KEY (payee_id) REFERENCES users(id)
);

INSERT INTO users (first_name, last_name, email, document, password, balance, user_type)
VALUES
    ('Marcelo', 'Coldibelli', 'marcelo@mail.com', '12345678901', 'senha123', 100.00, 'COMMON'),
    ('Augusto', 'Bersan', 'augusto@hotmail.com', '98765432109', 'senha456', 0.00, 'COMMON'),
    ('Natalia', 'Peres', 'natalia@example.com', '45612378901', 'senha789', 500.00, 'MERCHANT');
