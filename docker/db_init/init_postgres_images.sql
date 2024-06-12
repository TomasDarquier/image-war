
CREATE TABLE IF NOT EXISTS daily_tops(
    id SERIAL PRIMARY KEY,
    image_id INT NOT NULL,
    user_id INT NOT NULL,
    date DATE NOT NULL,
    total_votes INT NOT NULL,
    place INT NOT NULL CHECK (place BETWEEN 1 AND 10),
    UNIQUE (date, place)
);

CREATE INDEX idx_daily_tops_date ON daily_tops(date);
CREATE INDEX idx_daily_tops_user_id ON daily_tops(user_id);

CREATE TABLE IF NOT EXISTS vote_counts(
    id SERIAL PRIMARY KEY,
    image_id INT NOT NULL,
    date DATE NOT NULL,
    total_votes INT NOT NULL
);

CREATE INDEX idx_vote_counts_image_id ON vote_counts(image_id);

CREATE TABLE IF NOT EXISTS votes(
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    image_id INT NOT NULL
);
