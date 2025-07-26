-- Insert sample user (password is 'password' encoded with BCrypt)
INSERT INTO users (username, email, password, role, enabled) 
VALUES ('admin', 'admin@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', 'ADMIN', true)
ON CONFLICT (username) DO NOTHING; 