CREATE TABLE IF NOT EXISTS images (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    path VARCHAR(255) NOT NULL,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(15) CHECK (status IN ('processing', 'banned', 'approved'))
);
