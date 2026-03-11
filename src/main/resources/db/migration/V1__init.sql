CREATE TABLE users (
    name TEXT PRIMARY KEY,
    age INT NOT NULL,
    height DOUBLE PRECISION NOT NULL,
    pinned_exercise_name VARCHAR(255)
);

CREATE TABLE weight_measurements (
    id UUID PRIMARY KEY,
    user_name TEXT NOT NULL,
    weight DOUBLE PRECISION NOT NULL,
    date DATE NOT NULL,
    weight_measurements_key INT,
    CONSTRAINT fk_user_weight FOREIGN KEY (user_name) REFERENCES users(name) ON DELETE CASCADE
);

CREATE TABLE exercises (
    name TEXT,
    user_name TEXT,
    PRIMARY KEY (name, user_name),
    CONSTRAINT fk_user_exercise FOREIGN KEY (user_name) REFERENCES users(name) ON DELETE CASCADE
);

CREATE TABLE exercise_sets (
    id BIGSERIAL PRIMARY KEY,
    exercise_name TEXT NOT NULL,
    user_name TEXT NOT NULL,
    reps INT NOT NULL,
    weight DOUBLE PRECISION NOT NULL,
    date DATE NOT NULL,
    CONSTRAINT fk_exercise_set FOREIGN KEY (exercise_name, user_name) REFERENCES exercises(name, user_name) ON DELETE CASCADE
);

ALTER TABLE users
ADD CONSTRAINT fk_pinned_exercise
FOREIGN KEY (pinned_exercise_name, name) REFERENCES exercises(name, user_name);
