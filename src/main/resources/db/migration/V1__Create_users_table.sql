-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id CHAR(26) PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);