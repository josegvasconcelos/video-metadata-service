-- Create users table username index
CREATE INDEX idx_users_username
    ON users USING btree (username);
