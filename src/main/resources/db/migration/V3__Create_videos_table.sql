-- Create users table
CREATE TABLE IF NOT EXISTS videos (
    id CHAR(26) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    source VARCHAR(255) NOT NULL,
    url VARCHAR(255) NOT NULL,
    duration_in_seconds BIGINT NOT NULL
);